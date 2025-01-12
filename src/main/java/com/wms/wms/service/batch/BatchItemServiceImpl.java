package com.wms.wms.service.batch;

import com.wms.wms.dto.request.batch.BatchItemUpdateRequest;
import com.wms.wms.entity.Batch;
import com.wms.wms.entity.BatchItem;
import com.wms.wms.entity.ProducedItem;
import com.wms.wms.entity.enumentity.status.BatchStatus;
import com.wms.wms.entity.enumentity.status.ItemStatus;
import com.wms.wms.exception.ConstraintViolationException;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.repository.BatchItemRepository;
import com.wms.wms.repository.BatchRepository;
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
    private final BatchRepository batchRepository;
    private final BatchService batchService;

    @Override
    @Transactional
    public List<BatchItem> findByProductId(Long productId) {
        List<BatchItem> items = batchItemRepository.findByProductId(productId);
        log.info("Service Batch item - Find items by product ID: {} successfully", productId);
        return items;
    }

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

        if (item.getWeight().equals(BigDecimal.ZERO)) {
            throw new ConstraintViolationException("Cannot mark an item as completed if it does not have a weight assigned");
        }
        item.setStatus(ItemStatus.COMPLETED);
        batchItemRepository.save(item);
        log.info("Service Batch item - update item status successfully");

        log.info("Service Batch item - Update batch status");
        batchService.updateStatusBasedOnItems(batch);
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
        if (item.getStatus() == ItemStatus.COMPLETED) {
            throw new ConstraintViolationException("Cannot delete completed batch item");
        }
        batchItemRepository.delete(item);
        log.info("Service Batch item - Delete item ID {} successfully", itemId);

    }

    @Override
    @Transactional
    public void updateProducedQuantity(ProducedItem producedItem) {
        BatchItem batchItem = getItemById(producedItem.getBatchItemId());
        BigDecimal newProducedQuantity = batchItem.getProducedQuantity().add(producedItem.getQuantity());
        batchItem.setProducedQuantity(newProducedQuantity);
        batchItemRepository.save(batchItem);
        log.info("Service Batch item - Update produced quantity batch item ID {} successfully", batchItem.getId());
    }

    private BatchItem getItemById(Long id) {
        return batchItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No batch item exists with the given Id: " + id));
    }
}
