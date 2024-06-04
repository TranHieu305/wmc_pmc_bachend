package com.wms.wms.service.impl;

import com.wms.wms.entity.AssignedOrderItem;
import com.wms.wms.entity.Lot;
import com.wms.wms.entity.enumentity.AssignedOrderItemStatus;
import com.wms.wms.exception.ConstraintViolationException;
import com.wms.wms.repository.AssignedOrderItemRepository;
import com.wms.wms.service.AssignedOrderItemService;
import com.wms.wms.service.EntityRetrievalService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssignedOrderItemServiceImpl implements AssignedOrderItemService {
    private final AssignedOrderItemRepository assignedOrderItemRepository;
    private final EntityRetrievalService entityRetrievalService;

    @Override
    @Transactional
    public void changeStatusToInTransit(int id) {

    }

    @Override
    @Transactional
    public void changeStatusToDelivered(int id) {
        AssignedOrderItem item = entityRetrievalService.getAssignedOrderItemById(id);
        this.checkCanUpdate(item);
        item.setStatus(AssignedOrderItemStatus.DELIVERED);
        item.setDeliveredDate(new Date());
        assignedOrderItemRepository.save(item);
        log.info("Change status to delivered successfully");
    }

    @Override
    @Transactional
    public void changeStatusToReturned(int id) {
        AssignedOrderItem item = entityRetrievalService.getAssignedOrderItemById(id);
        this.checkCanUpdate(item);
        item.setStatus(AssignedOrderItemStatus.RETURNED);
        item.setDeliveredDate(new Date());
        assignedOrderItemRepository.save(item);
        log.info("Change status to returned successfully");
    }

    private void checkCanUpdate(AssignedOrderItem assignedOrderItem) {
        Lot lot = assignedOrderItem.getLot();
        if (!lot.canUpdate()) {
            throw new ConstraintViolationException("Lot of this item cannot be update");
        }
    }
}
