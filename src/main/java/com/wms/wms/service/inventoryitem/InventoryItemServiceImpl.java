package com.wms.wms.service.inventoryitem;

import com.wms.wms.entity.*;
import com.wms.wms.entity.enumentity.type.InventoryAction;
import com.wms.wms.repository.InventoryItemRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class InventoryItemServiceImpl implements InventoryItemService {
    private final InventoryItemRepository inventoryItemRepository;

    @Override
    public List<InventoryItem> findAll() {
        return inventoryItemRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public InventoryItem findById(Long entityId) {
        return null;
    }

    @Override
    public InventoryItem save(InventoryItem inventoryItem) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    @Transactional
    public void processDeliveredBatchItems(Batch batch, InventoryAction action) {
        List<InventoryItem> itemList = batch.getBatchItems().stream().map(batchItem ->
                (InventoryItem) InventoryItem.builder()
                        .batchId(batch.getId())
                        .batchName(batch.getName())
                        .warehouseId(batch.getWarehouse().getId())
                        .warehouseName(batch.getWarehouse().getName())
                        .product(batchItem.getProduct())
                        .quantity(batchItem.getQuantity())
                        .uom(batchItem.getUom())
                        .inventoryAction(action)
                        .build()
        ).toList();
        log.info("Service Inventory item - Process delivered Batch Items successfully");
        inventoryItemRepository.saveAll(itemList);
    }

    @Override
    @Transactional
    public void processCompletedBatchItem(Batch batch, BatchItem item) {
        InventoryItem inventoryItem = InventoryItem.builder()
                .batchId(batch.getId())
                .batchName(batch.getName())
                .warehouseId(batch.getWarehouse().getId())
                .warehouseName(batch.getWarehouse().getName())
                .product(item.getProduct())
                .quantity(item.getQuantity())
                .uom(item.getUom())
                .inventoryAction(InventoryAction.IMPORT)
                .build();
        log.info("Service Inventory item - Process completed Batch Items successfully");
        inventoryItemRepository.save(inventoryItem);
    }
}
