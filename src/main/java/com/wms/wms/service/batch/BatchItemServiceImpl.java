package com.wms.wms.service.batch;

import com.wms.wms.dto.request.batch.BatchItemUpdateRequest;
import com.wms.wms.dto.response.batch.BatchItemResponse;
import com.wms.wms.entity.Batch;
import com.wms.wms.entity.BatchItem;
import com.wms.wms.entity.enumentity.BatchStatus;
import com.wms.wms.entity.enumentity.InventoryAction;
import com.wms.wms.entity.enumentity.ItemStatus;
import com.wms.wms.exception.ConstraintViolationException;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.repository.BatchItemRepository;
import com.wms.wms.repository.BatchRepository;
import com.wms.wms.service.product.ProductWarehouseHistoryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BatchItemServiceImpl implements BatchItemService {
    private final BatchItemRepository batchItemRepository;
    private final ProductWarehouseHistoryService pwhService;
    private final BatchRepository batchRepository;

    @Override
    @Transactional
    public List<BatchItem> findByProductId(Long productId) {
        List<BatchItem> items = batchItemRepository.findByProductId(productId);
        log.info("Service Batch item - Find items by product ID: {} successfully", productId);
        return items;
    }

    /**
     * Marks a specific batch item as complete and updates the associated batch status if necessary.

     * Functional Details:
     * - Retrieves the batch by its ID. Throws a ResourceNotFoundException if the batch does not exist.
     * - Finds the batch item within the batch by its ID. Throws a ResourceNotFoundException if the item is not found.
     * - Validates the item's quantity. Throws a ConstraintViolationException if the quantity is zero.
     * - Updates the item's status to COMPLETED and saves the item.
     * - If the batch's inventory and order actions are EXPORT, processes the completed item using the PWH service.
     * - Updates the batch's status:
     *   - Changes to PACKING if the batch is in PENDING state and the first item is marked as complete.
     *   - Changes to COMPLETED if all items in the batch are marked as complete.
     *
     * @param itemId  the ID of the batch item to be marked as complete
     * @param batchId the ID of the batch containing the item
     * @throws ResourceNotFoundException if the batch or batch item does not exist
     * @throws ConstraintViolationException if the item quantity is zero
     */
    @Override
    @Transactional
    public void markAsComplete(Long itemId, Long batchId) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new ResourceNotFoundException("No Batch exists with the given Id: " + batchId));
        BatchItem item = batch.getBatchItems()
                .stream()
                .filter(item1 -> item1.getId().equals(itemId))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("No Batch item exists with the given Id: " + itemId));

        if (item.getQuantity().equals(BigDecimal.ZERO)) {
            throw new ConstraintViolationException("Cannot mark an item as completed if it does not have a weight assigned");
        }
        item.setStatus(ItemStatus.COMPLETED);
        batchItemRepository.save(item);
        log.info("Service Batch item - update item status successfully");

        if (batch.getInventoryAction() == InventoryAction.EXPORT &&
            batch.getOrderInventoryAction() == InventoryAction.EXPORT) {

            log.info("Service Batch item - process completed item");
            pwhService.processImportBatchItem(item, batch.getWarehouse());
            log.info("Service Batch item - process completed item successfully");
        }

        // Change status of batch
        // If first item completed, change status batch to PACKING
        // If all item completed, change status to COMPLETED
        if (batch.getStatus() == BatchStatus.PENDING) {
            batch.setStatus(BatchStatus.PACKING);
            log.info("Service Batch item - Change batch status to PACKING successfully");
        }

        boolean isCompletedBatch = true;
        for (BatchItem item1 : batch.getBatchItems()) {
            if (item1.getStatus() != ItemStatus.COMPLETED) {
                isCompletedBatch = false;
                break;
            }
        }
        if (isCompletedBatch) {
            batch.setStatus(BatchStatus.COMPLETED);
            log.info("Service Batch item - Change batch status to COMPLETED successfully");
        }
    }

    @Override
    public void markAsCompleteAll(Batch batch) {

    }

    @Override
    @Transactional
    public void updateItem(BatchItemUpdateRequest request) {
        BatchItem item = this.getItemById(request.getId());
        item.setWeight(request.getWeight());

        if (item.getStatus() != ItemStatus.COMPLETED) {
            item.setQuantity(request.getQuantity());
        }

        batchItemRepository.save(item);
        log.info("Service Batch item - Update item ID {} successfully", item.getId());

    }

    @Override
    @Transactional
    public void deleteItem(Long itemId) {
        BatchItem item = this.getItemById(itemId);
        if (item.getStatus() != ItemStatus.COMPLETED) {
            throw new ConstraintViolationException("Cannot delete completed batch item");
        }
        batchItemRepository.delete(item);
        log.info("Service Batch item - Delete item ID {} successfully", itemId);

    }

    private BatchItem getItemById(Long id) {
        return batchItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No batch item exists with the given Id: " + id));
    }
}
