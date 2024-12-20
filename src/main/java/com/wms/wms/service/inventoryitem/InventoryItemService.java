package com.wms.wms.service.inventoryitem;

import com.wms.wms.entity.Batch;
import com.wms.wms.entity.InventoryItem;
import com.wms.wms.entity.enumentity.InventoryAction;
import com.wms.wms.service.BaseService;

import java.util.List;

public interface InventoryItemService extends BaseService<InventoryItem, InventoryItem> {
    void processDeliveredBatchItems (Batch batch, InventoryAction action);
}
