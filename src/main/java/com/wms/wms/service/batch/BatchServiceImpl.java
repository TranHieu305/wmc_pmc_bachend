package com.wms.wms.service.batch;

import com.wms.wms.dto.request.batch.BatchItemRequest;
import com.wms.wms.dto.request.batch.BatchRequest;
import com.wms.wms.dto.response.batch.BatchResponse;
import com.wms.wms.entity.*;
import com.wms.wms.entity.enumentity.BatchStatus;
import com.wms.wms.entity.enumentity.InventoryAction;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.batch.BatchResponseMapper;
import com.wms.wms.repository.*;
import com.wms.wms.service.inventoryitem.InventoryItemService;
import com.wms.wms.service.product.ProductWarehouseHistoryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BatchServiceImpl implements BatchService{
    private final BatchRepository batchRepository;
    private final OrderRepository orderRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductWarehouseHistoryService pwhService;
    private final InventoryItemService inventoryItemService;

    @Override
    @Transactional
    public BatchResponse create(BatchRequest batchRequest) {
        Batch newBatch = new Batch();
        newBatch.setName(batchRequest.getName());
        newBatch.setBatchDate(batchRequest.getBatchDate());
        newBatch.setInventoryAction(batchRequest.getInventoryAction());

        // Validate and set order
        Order order = orderRepository.findById(batchRequest.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("No Order exists with the given Id: " + batchRequest.getOrderId()));
        newBatch.setOrder(order);
        newBatch.setPartner(order.getPartner());
        newBatch.setOrderInventoryAction(order.getInventoryAction());

        // Validate and set warehouse
        Warehouse warehouse = warehouseRepository.findById(batchRequest.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("No Batch warehouse exists with the given Id: " + batchRequest.getWarehouseId()));
        newBatch.setWarehouse(warehouse);

        // Set BatchItems to Batch
        List<BatchItem> batchItemList = this.toBatchItems(batchRequest.getBatchItemRequests(), order);
        for (BatchItem item : batchItemList) {
            newBatch.addItem(item);
        }

        // Save to db
        Batch dbBatch = batchRepository.save(newBatch);
        log.info("Service Batch - Save Batch successfully with ID: {}", dbBatch.getId());

        return BatchResponseMapper.INSTANCE.toDto(dbBatch);
    }

    private List<BatchItem> toBatchItems (List<BatchItemRequest> batchItemRequests, Order order) {
        // Find orderItem from Order
        return batchItemRequests.stream().map(
            batchItemRequest -> {
                Long orderItemId = batchItemRequest.getOrderItemId();
                OrderItem orderItem = order.getOrderItems()
                        .stream()
                        .filter(orderItem1 -> orderItem1.getId().equals(orderItemId))
                        .findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException("Order item with ID" + orderItemId + "is not exist"));

                return (BatchItem) BatchItem.builder()
                        .orderItemId(orderItem.getId())
                        .weight(batchItemRequest.getWeight())
                        .quantity(batchItemRequest.getQuantity())
                        .product(orderItem.getProduct())
                        .uom(orderItem.getProduct().getUom())
                        .build();
            }
        ).toList();
    }


    @Override
    @Transactional
    public List<BatchResponse> findAll() {
        List<Batch> dbBatch = batchRepository.findAll();
        log.info("Service Batch - Get all batches successfully");
        return BatchResponseMapper.INSTANCE.toDtoList(dbBatch);
    }

    @Override
    @Transactional
    public BatchResponse findById(Long entityId) {
        Batch dbBatch = this.getBatchById(entityId);
        log.info("Service Batch - Get batch ID {} successfully", entityId);
        return BatchResponseMapper.INSTANCE.toDto(dbBatch);
    }

    @Override
    public BatchResponse save(BatchRequest batchRequest) {
        return null;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Batch batch = this.getBatchById(id);
        batchRepository.delete(batch);
        log.info("Service Batch - Delete batch ID {} successfully", id);
    }

    @Override
    @Transactional
    public void markAsDelivered(Long batchId) {
        Batch batch = this.getBatchById(batchId);

        // Update the status
        batch.setStatus(BatchStatus.DELIVERED);
        Batch updatedBatch = batchRepository.save(batch);
        log.info("Service Batch - Mark as delivered batch ID {} successfully", batchId);

        // Process DELIVERED batch items
        if (updatedBatch.getInventoryAction().equals(InventoryAction.IMPORT)) {
            log.info("Service Batch - Start process import batch items");
            pwhService.processImportBatchItems(updatedBatch);
            inventoryItemService.processDeliveredBatchItems(updatedBatch);
            log.info("Service Batch - Process import batch items successfully");
        }
    }

    private Batch getBatchById(Long id) {
        return batchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Batch exists with the given Id: " + id));
    }
}
