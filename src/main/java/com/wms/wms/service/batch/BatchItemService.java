package com.wms.wms.service.batch;

import com.wms.wms.dto.request.batch.BatchItemUpdateRequest;
import com.wms.wms.dto.response.batch.BatchItemResponse;
import com.wms.wms.entity.Batch;
import com.wms.wms.entity.BatchItem;
import com.wms.wms.entity.ProducedItem;

import java.util.List;


public interface BatchItemService {
    List<BatchItem> findByProductId(Long productId);

    void markAsComplete (Long itemId, Long batchId);

    void markAsCompleteAll (Batch batch);

    void updateItem (BatchItemUpdateRequest request);

    void deleteItem (Long itemId);

    void updateProducedQuantity(ProducedItem item);
}
