package com.wms.wms.service.impl;

import com.wms.wms.dto.request.AssignedOrderItemRequest;
import com.wms.wms.dto.request.LotRequest;
import com.wms.wms.dto.response.lot.LotDetailResponse;
import com.wms.wms.entity.*;
import com.wms.wms.entity.enumentity.LotStatus;
import com.wms.wms.entity.enumentity.LotType;
import com.wms.wms.exception.ConstraintViolationException;
import com.wms.wms.repository.LotRepository;
import com.wms.wms.service.*;
import com.wms.wms.util.StringHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LotServiceImpl implements LotService {
    private final LotRepository lotRepository;
    private final EntityRetrievalService entityRetrievalService;
    private final ProductWarehouseService productWarehouseService;
    private final InventoryItemService inventoryItemService;
    private final AssignedOrderItemService assignedOrderItemService;

    @Override
    @Transactional
    public LotDetailResponse save(LotRequest request) {
        // Validate order
        AbstractOrder order = entityRetrievalService.getOrderById(request.getOrderId());
        // Validate warehouse
        Warehouse warehouse = entityRetrievalService.getWarehouseById(request.getWarehouseId());

        // Create/get Lot
        Lot lot;
        if (request.getId() != 0) {
            lot = entityRetrievalService.getLotById(request.getId());
        }
        else {
            lot = Lot.builder().build();
        }
        // Map fields
        lot.setOrderId(order.getId());
        lot.setWarehouseId(warehouse.getId());
        lot.setName(StringHelper.preProcess(request.getName()));
        lot.setDescription(request.getDescription());
        lot.setStatus(request.getStatus());
        lot.setDate(request.getDate());
        if (order instanceof MaterialOrder) {
            lot.setType(LotType.MATERIAL);
        }
        else {
            lot.setType(LotType.PRODUCTION);
        }

        // Remove Old AssignedItems
        if (!lot.getAssignedOrderItems().isEmpty()) {
            List<AssignedOrderItem> existingItems = new ArrayList<>(lot.getAssignedOrderItems());
            existingItems.forEach(lot::removeAssignedOrderItem);
        }

        // Save New AssignedItems
        if (!request.getAssignedOrderItems().isEmpty()) {
            List<AssignedOrderItem> newItems = this.convertToAssignedOrderItem(request.getAssignedOrderItems());
            newItems.forEach(lot::addAssignedOrderItem);
        }

        // Save to db
        Lot dbLot = lotRepository.save(lot);
        log.info("Add lot successfully");
        return this.convertToDetailResponse(dbLot);
    }

    private List<AssignedOrderItem> convertToAssignedOrderItem(List<AssignedOrderItemRequest> request) {
        return request.stream().map(requestDTO -> {
            // Validate OrderItem
            OrderItem orderItem = entityRetrievalService.getOrderItemById(requestDTO.getOrderItemId());

            // Get other assigned item of this order item
            List<AssignedOrderItem> assignedItemsForThisOrderItem =
                    entityRetrievalService.getAssignedOrderItemByOrderItemId(orderItem.getId());

            // Get sum quantity of assigned items
            BigDecimal sumAssignedQuantity = BigDecimal.ZERO;
            for (AssignedOrderItem item : assignedItemsForThisOrderItem) {
                sumAssignedQuantity = sumAssignedQuantity.add(item.getAssignedQuantity());
            }

            // Create/Get assigned order item
            AssignedOrderItem item;
            if (requestDTO.getId() != 0) {
                item = entityRetrievalService.getAssignedOrderItemById(requestDTO.getId());
            }
            else {
                item = AssignedOrderItem.builder().build();
            }
            // Get sum quantity except this item's quantity
            if (item.getAssignedQuantity() != null) {
                sumAssignedQuantity = sumAssignedQuantity.subtract(item.getAssignedQuantity());
            }
            // Validate if quantity of request
            int compare = sumAssignedQuantity.add(requestDTO.getAssignedQuantity())
                    .compareTo(orderItem.getQuantity());
            if (compare > 0) {
                throw new ConstraintViolationException("Quantity assigned exceed remain quantity of order item");
            }

            // Map field
            item.setOrderItemId(orderItem.getId());
            item.setProductId(orderItem.getProduct().getId());
            item.setAssignedTo(requestDTO.getAssignedTo());
            item.setAssignedQuantity(requestDTO.getAssignedQuantity());
            item.setStatus(requestDTO.getStatus());

            return item;
        }).toList();
    }

    private LotDetailResponse convertToDetailResponse(Lot lot) {
        return LotDetailResponse.builder()
                .id(lot.getId())
                .name(lot.getName())
                .description(lot.getDescription())
                .orderId(lot.getOrderId())
                .warehouseId(lot.getWarehouseId())
                .status(lot.getStatus())
                .date(lot.getDate())
                .assignedOrderItems(lot.getAssignedOrderItems())
                .build();
    }

    @Override
    public List<LotDetailResponse> findAll() {
        List<Lot> dbLotList = lotRepository.findAll();
        List<LotDetailResponse> response = dbLotList.stream().map(this::convertToDetailResponse).toList();
        log.info("Get all lots successfully");
        return response;
    }

    @Override
    @Transactional
    public void changeStatusToCompleted(int lotId) {
        Lot lot = entityRetrievalService.getLotById(lotId);
        lot.setStatus(LotStatus.COMPLETED);
        // Change all not-completed to completed
        // TODO:


        if (lot.isMaterialLot()) {
            // Convert lot items to inventory item
            inventoryItemService.processCompletedLot(lot);
        }
        // Add lot items to warehouse
        productWarehouseService.processCompletedLot(lot);
    }

}
