package com.wms.wms.service.batch;

import com.wms.wms.dto.request.batch.BatchItemUpdateRequest;
import com.wms.wms.entity.Batch;
import com.wms.wms.entity.BatchItem;

import java.util.List;


public interface BatchItemService {
    /**
     * Change status of item to COMPLETED, then call productWarehouseHistory
     * to create IMPORT history
     *
     * @param itemId ID of the item
     * @param batchId ID of the batch
     */
    void markAsComplete (Long itemId, Long batchId);

    void markAsCompleteAll (Batch batch);

    void updateItem (BatchItemUpdateRequest request);

    void deleteItem (Long itemId);
}
