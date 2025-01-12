package com.wms.wms.service.produceditem;

import com.wms.wms.dto.request.produceditem.ProducedItemRequest;
import com.wms.wms.entity.*;
import com.wms.wms.entity.enumentity.status.BatchStatus;
import com.wms.wms.entity.enumentity.status.ItemStatus;
import com.wms.wms.entity.enumentity.status.OrderStatus;
import com.wms.wms.entity.enumentity.status.ProducedItemStatus;
import com.wms.wms.entity.enumentity.type.InventoryAction;
import com.wms.wms.exception.ConstraintViolationException;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.repository.BatchItemRepository;
import com.wms.wms.repository.BatchRepository;
import com.wms.wms.repository.ProducedItemRepository;
import com.wms.wms.service.batch.BatchItemService;
import com.wms.wms.service.product.ProductWarehouseHistoryService;
import com.wms.wms.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProducedItemServiceImpl implements ProducedItemService{
    private final ProducedItemRepository producedItemRepository;
    private final BatchRepository batchRepository;
    private final BatchItemRepository batchItemRepository;
    private final UserService userService;
    private final ProductWarehouseHistoryService pwhService;
    private final BatchItemService batchItemService;

    @Override
    public List<ProducedItem> findAll() {
        return null;
    }

    @Override
    public List<ProducedItem> findByBatchId(long batchId) {
        List<ProducedItem> items = producedItemRepository.findByBatchIdOrderByCreatedAtDesc(batchId);
        log.info("Service Produced Item - Get items by batchId: {} successfully", batchId);
        return items;
    }

    @Override
    public ProducedItem findById(Long entityId) {
        return null;
    }

    @Override
    public ProducedItem save(ProducedItemRequest producedItemRequest) {
        return null;
    }

    @Override
    @Transactional
    public ProducedItem create(ProducedItemRequest request) {
        BatchItem batchItem = batchItemRepository.findById(request.getBatchItemId())
                .orElseThrow(() -> new ResourceNotFoundException("No batch item exists with the given Id: " + request.getBatchItemId()));
        if (batchItem.getStatus() == ItemStatus.COMPLETED) {
            throw new ConstraintViolationException("Cannot produce completed batch item");
        }
        Batch batch = batchRepository.findById(batchItem.getBatchId())
                .orElseThrow(() -> new ResourceNotFoundException("No batch exists with the given Id: " + batchItem.getBatchId()));
        User currentUser = userService.getCurrentUser();
        if (!batch.getParticipantIds().contains(currentUser.getId())) {
            throw new ConstraintViolationException("User are not participant of batch Id: "+ batch.getId());
        }

        ProducedItem producedItem = ProducedItem.builder()
                .product(batchItem.getProduct())
                .quantity(request.getQuantity())
                .uom(batchItem.getProduct().getUom())
                .batchId(batch.getId())
                .batchItemId(batchItem.getId())
                .approverIds(new HashSet<>(request.getApproverIds()))
                .pendingApproverIds(new HashSet<>(request.getApproverIds()))
                .build();
        producedItem = producedItemRepository.save(producedItem);
        log.info("Service Produced Item - Create item successfully");

        return producedItem;
    }

    @Override
    public ProducedItem update(ProducedItemRequest request) {
        return null;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        ProducedItem item = getById(id);
        producedItemRepository.delete(item);
        log.info("Service Produced Item - Delete item id: {} successfully", id);
    }

    @Override
    @Transactional
    public void approve(Long itemId) {
        ProducedItem item = getById(itemId);
        // Check constraint
        User currentUser = userService.getCurrentUser();
        checkConstraintToApprove(item, currentUser);

        // Remove from pending approver
        item.getPendingApproverIds().remove(currentUser.getId());
        // If all approvers approved, change status
        if (item.getPendingApproverIds().isEmpty() &&
                item.getStatus() == ProducedItemStatus.PENDING_APPROVAL) {
            item.setStatus(ProducedItemStatus.APPROVED);
        }

        producedItemRepository.save(item);
        log.info("Service Produced Item - Approve item id: {} successfully", itemId);

        pwhService.importProducedItem(item);
        log.info("Service Batch item - Process import produced item to warehouse successfully");

        batchItemService.updateProducedQuantity(item);
        log.info("Service Batch item - Process update produced quantity batch item successfully");
    }

    @Override
    @Transactional
    public void reject(Long itemId) {
        ProducedItem item = getById(itemId);
        // Check constraint
        User currentUser = userService.getCurrentUser();
        checkConstraintToApprove(item, currentUser);

        // Process approve
        // Remove from pending approver
        item.getPendingApproverIds().remove(currentUser.getId());
        item.setStatus(ProducedItemStatus.APPROVED);
        producedItemRepository.save(item);
        log.info("Service Produced Item - Reject item id: {} successfully", itemId);
    }

    private ProducedItem getById(Long itemId) {
        return producedItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("No produced item exists with the given Id: " + itemId));
    }

    private void checkConstraintToApprove(ProducedItem item, User currentUser) {
        if (item.getStatus() != ProducedItemStatus.PENDING_APPROVAL) {
            throw new ConstraintViolationException("Cannot approve item does not have pending approval status");
        }
        if (!item.getApproverIds().contains(currentUser.getId())) {
            throw new ConstraintViolationException("You are not approver");
        }
        if (!item.getPendingApproverIds().contains(currentUser.getId())) {
            throw new ConstraintViolationException("You had already approved");
        }
    }
}
