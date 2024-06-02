package com.wms.wms.service.impl;

import com.wms.wms.entity.*;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.repository.InventoryItemRepository;
import com.wms.wms.service.EntityRetrievalService;
import com.wms.wms.service.InventoryItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void processCompletedLot(Lot lot) {
        Warehouse warehouse = entityRetrievalService.getWarehouseById(lot.getWarehouseId());
        List<AssignedOrderItem> items = lot.getAssignedOrderItems();

        // Get Order items from Material Order and assigned
        Set<Integer> orderItemIds = items.stream()
                .map(AbstractEntity::getId)
                .collect(Collectors.toSet());
        List<OrderItem> orderItems = entityRetrievalService.getOrderItemByIds(orderItemIds);

        // Convert order item to inventory_item
        List<InventoryItem> inventoryItems = items.stream()
                .map(item ->
                {
                    OrderItem orderItem = orderItems.stream()
                            .filter(orderItem1 -> orderItem1.getId() == item.getOrderItemId())
                            .findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("Order Item not exists to covert to inventory item"));

                    return InventoryItem.builder()
                            .assignedOrderItemId(item.getId())
                            .productId(item.getProductId())
                            .warehouseId(warehouse.getId())
                            .quantityOnHand(item.getAssignedQuantity())
                            .datetimeReceived(item.getDeliveredDate())
                            .unitPrice(orderItem.getProductPrice())
                            .build();
                })
                .toList();

        inventoryItemRepository.saveAll(inventoryItems);
        log.info("Convert OrderItem to InventoryItem successfully");
    }
}
