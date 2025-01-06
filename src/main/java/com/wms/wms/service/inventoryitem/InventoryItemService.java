package com.wms.wms.service.inventoryitem;

import com.wms.wms.entity.Batch;
import com.wms.wms.entity.BatchItem;
import com.wms.wms.entity.InventoryItem;
import com.wms.wms.entity.enumentity.type.InventoryAction;
import com.wms.wms.service.BaseService;

public interface InventoryItemService extends BaseService<InventoryItem, InventoryItem> {
    void processDeliveredBatchItems (Batch batch, InventoryAction action);

    void processCompletedBatchItem (Batch batch, BatchItem item);
}
