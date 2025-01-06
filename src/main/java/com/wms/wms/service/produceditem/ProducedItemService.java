package com.wms.wms.service.produceditem;

import com.wms.wms.dto.request.produceditem.ProducedItemRequest;
import com.wms.wms.entity.ProducedItem;
import com.wms.wms.service.BaseService;

import java.util.List;

public interface ProducedItemService extends BaseService<ProducedItemRequest, ProducedItem> {
    void approve(Long itemId);
    void reject(Long itemId);

    ProducedItem create(ProducedItemRequest request);

    ProducedItem update(ProducedItemRequest request);

    List<ProducedItem> findByBatchId(long batchId);
}
