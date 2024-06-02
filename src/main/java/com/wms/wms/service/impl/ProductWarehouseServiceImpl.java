package com.wms.wms.service.impl;


import com.wms.wms.entity.*;
import com.wms.wms.entity.enumentity.AssignedOrderItemStatus;
import com.wms.wms.repository.ProductWarehouseRepository;
import com.wms.wms.service.EntityRetrievalService;
import com.wms.wms.service.ProductWarehouseService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductWarehouseServiceImpl implements ProductWarehouseService {
    private final ProductWarehouseRepository productWarehouseRepository;
    private final EntityRetrievalService entityRetrievalService;

    @Override
    @Transactional
    public void processCompletedLot(Lot lot) {
        if (lot.isMaterialLot()) {
            this.importLotItemsToWarehouse(lot);
        }
        else {
            this.exportLotItemsFromWarehouse(lot);
        }
    }

    private void importLotItemsToWarehouse(Lot lot) {
        Warehouse warehouse = entityRetrievalService.getWarehouseById(lot.getWarehouseId());
        List<AssignedOrderItem> assignedOrderItemList = lot.getAssignedOrderItems();

        Set<Integer> productIds = assignedOrderItemList.stream()
                .map(AssignedOrderItem::getProductId)
                .collect(Collectors.toSet());

        List<ProductWarehouse> products = entityRetrievalService.findByWarehouseIdAndProductIdIn(lot.getWarehouseId(), productIds);

        for (AssignedOrderItem item : assignedOrderItemList) {
            // Only import delivered item
            if (!item.getStatus().equals(AssignedOrderItemStatus.DELIVERED)) {
                continue;
            }
            // Find existing product with the same product ID
            ProductWarehouse product = products.stream()
                    .filter(p -> p.getProductId() == item.getProductId())
                    .findFirst()
                    .orElse(null);

            // If not found, create new
            if (product == null) {
                product = ProductWarehouse.builder()
                        .warehouseId(warehouse.getId())
                        .build();
            }
            // Add quantity from item with status DELIVERED to product warehouse
            BigDecimal sumQuantity = product.getQuantityOnHand().add(item.getAssignedQuantity());
            product.setQuantityOnHand(sumQuantity);
        }

        productWarehouseRepository.saveAll(products);
        log.info("Import Items from Material Lot to Warehouse successfully");
    }

    private void exportLotItemsFromWarehouse(Lot lot) {
        // TODO
    }
}