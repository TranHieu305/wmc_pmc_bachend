package com.wms.wms.service.batch;

import com.wms.wms.dto.request.batch.BatchItemRequest;
import com.wms.wms.dto.request.batch.BatchRequest;
import com.wms.wms.dto.request.batch.BatchUpdateRequest;
import com.wms.wms.dto.response.batch.BatchResponse;
import com.wms.wms.entity.*;
import com.wms.wms.entity.enumentity.status.BatchStatus;
import com.wms.wms.entity.enumentity.type.InventoryAction;
import com.wms.wms.entity.enumentity.status.ItemStatus;
import com.wms.wms.exception.ConstraintViolationException;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.batch.BatchResponseMapper;
import com.wms.wms.repository.*;
import com.wms.wms.service.inventoryitem.InventoryItemService;
import com.wms.wms.service.product.ProductWarehouseHistoryService;
import com.wms.wms.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BatchServiceImpl implements BatchService{
    private final BatchRepository batchRepository;
    private final OrderRepository orderRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductWarehouseHistoryService pwhService;
    private final InventoryItemService inventoryItemService;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;

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

        newBatch = batchRepository.save(newBatch);

        // Set BatchItems to Batch
        List<BatchItem> batchItemList = this.toBatchItems(batchRequest.getBatchItemRequests(), order);
        for (BatchItem item : batchItemList) {
            item.setBatchId(newBatch.getId());
            newBatch.addItem(item);
        }

        //Set approvers
        Set<Long> approverIds = new HashSet<>(batchRequest.getApproverIds());
        newBatch.setApproverIds(approverIds);
        newBatch.setPendingApproverIds(approverIds);

        //Set participants
        Set<Long> participantIds = new HashSet<>(batchRequest.getParticipantIds());
        User curentUser = userService.getCurrentUser();
        participantIds.add(curentUser.getId());
        newBatch.setParticipantIds(participantIds);

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
        List<BatchItem> items = batch.getBatchItems();
        for (BatchItem item : items) {
            if (item.getStatus() == ItemStatus.COMPLETED) {
                throw  new ConstraintViolationException("Cannot delete batch that have completed item");
            }
        }
        batchRepository.delete(batch);
        log.info("Service Batch - Delete batch ID {} successfully", id);
    }

    @Override
    @Transactional
    public void markAsDelivered(Long batchId) {
        Batch batch = this.getBatchById(batchId);

        // Update the status
        batch.setStatus(BatchStatus.DELIVERED);
        for (BatchItem batchItem : batch.getBatchItems()) {
            batchItem.setStatus(ItemStatus.COMPLETED);
        }
        Batch updatedBatch = batchRepository.save(batch);
        log.info("Service Batch - Mark as delivered batch ID {} successfully", batchId);

        // Process DELIVERED batch items
        if (updatedBatch.getInventoryAction().equals(InventoryAction.IMPORT)) {
            log.info("Service Batch - Start process import batch items");
            pwhService.processImportBatchItems(updatedBatch);
            inventoryItemService.processDeliveredBatchItems(updatedBatch, InventoryAction.IMPORT);
            log.info("Service Batch - Process import batch items successfully");
        }
        //TODO
        else {
            log.info("Service Batch - Start process export batch items");
            pwhService.processExportBatchItems(updatedBatch);
            inventoryItemService.processDeliveredBatchItems(updatedBatch, InventoryAction.EXPORT);
            log.info("Service Batch - Process export batch items successfully");
        }

        // Update DELIVERED quantity to order
        List<OrderItem> orderItems = batch.getOrder().getOrderItems();
        Map<Long, BigDecimal> batchItemQuantities = batch.getBatchItems().stream()
                .collect(Collectors.groupingBy(
                        BatchItem::getOrderItemId,
                        Collectors.reducing(BigDecimal.ZERO, BatchItem::getQuantity, BigDecimal::add)
                ));

        boolean isSameInventoryAction = batch.getInventoryAction() == batch.getOrderInventoryAction();

        orderItems.forEach(orderItem -> {
            BigDecimal batchQuantity = batchItemQuantities.getOrDefault(orderItem.getId(), BigDecimal.ZERO);
            BigDecimal deliveredQuantity = orderItem.getDeliveredQuantity();

            if (isSameInventoryAction) {
                deliveredQuantity = deliveredQuantity.add(batchQuantity);
            } else {
                deliveredQuantity = deliveredQuantity.subtract(batchQuantity);
            }

            orderItem.setDeliveredQuantity(deliveredQuantity);
        });
        orderItemRepository.saveAll(orderItems);
        log.info("Service Batch - Update quantity delivered order items successfully");
    }

    @Override
    @Transactional
    public BatchResponse update(BatchUpdateRequest request) {
        Batch dbBatch = this.getBatchById(request.getId());
        if (dbBatch.getStatus() == BatchStatus.DELIVERED) {
            throw new ConstraintViolationException("Cannot update delivered batch");
        }
        dbBatch.setName(request.getName());
        dbBatch.setBatchDate(request.getBatchDate());

        Batch newBatch = batchRepository.save(dbBatch);
        log.info("Service Batch - Save Batch successfully with ID: {}", newBatch.getId());

        return BatchResponseMapper.INSTANCE.toDto(newBatch);
    }

    @Override
    public void addItem(Long batchId, BatchItemRequest itemRequest) {
        Batch batch = this.getBatchById(batchId);

        Optional<BatchItem> existItem = batch.getBatchItems()
                .stream()
                .filter(item -> item.getOrderItemId().equals(itemRequest.getOrderItemId()))
                .findFirst();

        if (existItem.isPresent() && existItem.get().getStatus() != ItemStatus.COMPLETED) {
            BatchItem item = existItem.get();
            BigDecimal newQuantity = item.getQuantity().add(itemRequest.getQuantity());
            BigDecimal newWeight = item.getWeight().add(itemRequest.getWeight());

            item.setQuantity(newQuantity);
            item.setWeight(newWeight);
        }
        else {
            OrderItem orderItem = orderItemRepository.findById(itemRequest.getOrderItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("No order item exists"));
            BatchItem newItem = BatchItem.builder()
                    .orderItemId(orderItem.getId())
                    .product(orderItem.getProduct())
                    .uom(orderItem.getUom())
                    .weight(itemRequest.getWeight())
                    .quantity(itemRequest.getQuantity())
                    .batchId(batch.getId())
                    .build();

            batch.addItem(newItem);
        }

        batchRepository.save(batch);
        log.info("Service Batch - Add item successfully");
    }
    @Override
    @Transactional
    public void approve(Long batchId) {
        Batch batch = getBatchById(batchId);
        // Check constraint
        User currentUser = userService.getCurrentUser();
        checkConstraintToApprove(batch, currentUser);

        // Process approve
        // Remove from pending approver
        batch.getPendingApproverIds().remove(currentUser.getId());
        // If all approvers approved, change status
        if (batch.getPendingApproverIds().isEmpty() &&
                batch.getStatus() == BatchStatus.PENDING_APPROVAL) {
            batch.setStatus(BatchStatus.PENDING);
        }

        batchRepository.save(batch);
        log.info("Service Batch - Approve batch ID {} successfully", batchId);
    }

    @Override
    @Transactional
    public void reject(Long batchId) {
        Batch batch = getBatchById(batchId);
        // Check constraint
        User currentUser = userService.getCurrentUser();
        checkConstraintToApprove(batch, currentUser);

        // Process approve
        // Remove from pending approver
        batch.getPendingApproverIds().remove(currentUser.getId());
        batch.setStatus(BatchStatus.REJECTED);
        batchRepository.save(batch);
        log.info("Service Batch - Reject batch ID {} successfully", batchId);
    }

    private void checkConstraintToApprove(Batch batch, User currentUser) {
        if (batch.getStatus() != BatchStatus.PENDING_APPROVAL) {
            throw new ConstraintViolationException("Cannot approve batch does not have pending approval status");
        }
        if (!batch.getApproverIds().contains(currentUser.getId())) {
            throw new ConstraintViolationException("You are not approver");
        }
        if (!batch.getPendingApproverIds().contains(currentUser.getId())) {
            throw new ConstraintViolationException("You had already approved");
        }
    }

    /**
     * If first item completed, change status batch to PACKING
     * If all item completed, change status to COMPLETED
     * @param {Batch} batch
     */
    @Override
    @Transactional
    public void updateStatusBasedOnItems(Batch batch) {
        if (batch.getStatus() == BatchStatus.PENDING) {
            batch.setStatus(BatchStatus.PACKING);
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
        }
        batchRepository.save(batch);
        log.info("Service Batch - Change batch status to {} successfully", batch.getStatus());
    }

    private Batch getBatchById(Long id) {
        return batchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Batch exists with the given Id: " + id));
    }
}
