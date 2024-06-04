package com.wms.wms.service.impl;

import com.wms.wms.entity.*;
import com.wms.wms.entity.enumentity.AssignedOrderItemStatus;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.repository.InventoryItemRepository;
import com.wms.wms.service.EntityRetrievalService;
import com.wms.wms.service.InventoryItemService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class InventoryItemServiceImpl implements InventoryItemService {
    private final InventoryItemRepository inventoryItemRepository;
    private final EntityRetrievalService entityRetrievalService;

    @Override
    public List<InventoryItem> findAll() {
        return inventoryItemRepository.findAllByOrderByDatetimeReceivedDesc();
    }

    /**
     * Convert DELIVERED assigned order item to Inventory item
     *
     * @param lot
     */
    @Override
    @Transactional
    public void convertToInventoryItem(Lot lot) {
        Warehouse warehouse = entityRetrievalService.getWarehouseById(lot.getWarehouseId());
        List<AssignedOrderItem> items = lot.getAssignedOrderItems();

        // Get Order items from Material Order and assigned
        Set<Integer> orderItemIds = items.stream()
                .map(AssignedOrderItem::getOrderItemId)
                .collect(Collectors.toSet());
        List<OrderItem> orderItems = entityRetrievalService.getOrderItemByIds(orderItemIds);

        List<InventoryItem> inventoryItems = new ArrayList<>();

        for (AssignedOrderItem assignedOrderItem: items) {
         if (assignedOrderItem.getStatus().equals(AssignedOrderItemStatus.DELIVERED)) {
             OrderItem orderItem = orderItems.stream()
                     .filter(orderItem1 -> orderItem1.getId() == assignedOrderItem.getOrderItemId())
                     .findFirst()
                     .orElseThrow(() -> new ResourceNotFoundException("Order Item not exists to covert to inventory item"));

             inventoryItems.add(InventoryItem.builder()
                     .lotId(lot.getId())
                     .assignedOrderItemId(assignedOrderItem.getId())
                     .productId(assignedOrderItem.getProductId())
                     .warehouseId(warehouse.getId())
                     .quantityOnHand(assignedOrderItem.getAssignedQuantity())
                     .datetimeReceived(assignedOrderItem.getDeliveredDate())
                     .unitPrice(orderItem.getProductPrice())
                     .build());
            }
        }

        if (!inventoryItems.isEmpty()) {
            inventoryItemRepository.saveAll(inventoryItems);
        }
        log.info("Convert OrderItem to InventoryItem successfully");
    }
}
