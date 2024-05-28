package com.wms.wms.service.impl;

import com.wms.wms.entity.InventoryItem;
import com.wms.wms.entity.OrderItem;
import com.wms.wms.entity.Product;
import com.wms.wms.repository.InventoryItemRepository;
import com.wms.wms.repository.ProductRepository;
import com.wms.wms.repository.WarehouseRepository;
import com.wms.wms.service.IInventoryItemService;
import com.wms.wms.service.IProductService;
import com.wms.wms.service.IWarehouseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class InventoryItemServiceImpl implements IInventoryItemService {
    private final InventoryItemRepository inventoryItemRepository;
    private final IProductService productService;
//    private final WarehouseRepository warehouseRepository;

    @Override
    public void convertOrderItemsToInventoryItems(List<OrderItem> orderItems) {
        List<InventoryItem> inventoryItems = orderItems.stream()
                .map(this::createInventoryItemFromOrderItem)
                .toList();

        inventoryItemRepository.saveAll(inventoryItems);
    }

    private InventoryItem createInventoryItemFromOrderItem(OrderItem orderItem) {
        return null;
    }
}
