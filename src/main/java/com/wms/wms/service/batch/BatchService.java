package com.wms.wms.service.batch;

import com.wms.wms.dto.request.batch.BatchItemRequest;
import com.wms.wms.dto.request.batch.BatchRequest;
import com.wms.wms.dto.request.batch.BatchUpdateRequest;
import com.wms.wms.dto.response.batch.BatchResponse;
import com.wms.wms.entity.Batch;
import com.wms.wms.entity.enumentity.status.BatchStatus;
import com.wms.wms.service.BaseService;

import java.util.List;

public interface BatchService extends BaseService<BatchRequest, BatchResponse> {
    BatchResponse create (BatchRequest request);

    BatchResponse update (BatchUpdateRequest request);

    void addItem (Long batchId, BatchItemRequest itemRequest);

    void markAsDeliveredWithoutShipment(Long batchId);

    void approve(Long batchId);

    void reject(Long batchId);

    void updateStatusBasedOnItems(Batch batch);

    List<BatchResponse> findByStatus(BatchStatus status);
}
