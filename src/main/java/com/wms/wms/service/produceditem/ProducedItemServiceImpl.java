package com.wms.wms.service.produceditem;

import com.wms.wms.dto.request.produceditem.ProducedItemRequest;
import com.wms.wms.entity.Batch;
import com.wms.wms.entity.BatchItem;
import com.wms.wms.entity.ProducedItem;
import com.wms.wms.entity.User;
import com.wms.wms.entity.enumentity.status.ItemStatus;
import com.wms.wms.exception.ConstraintViolationException;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.repository.BatchItemRepository;
import com.wms.wms.repository.BatchRepository;
import com.wms.wms.repository.ProducedItemRepository;
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
                .batchId(batch.getId())
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

    }

    @Override
    public void reject(Long itemId) {

    }

    private ProducedItem getById(Long itemId) {
        return producedItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("No produced item exists with the given Id: " + itemId));
    }
}
