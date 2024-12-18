package com.wms.wms.service.batch;

import com.wms.wms.dto.request.batch.BatchRequest;
import com.wms.wms.dto.response.batch.BatchResponse;
import com.wms.wms.service.BaseService;

public interface BatchService extends BaseService<BatchRequest, BatchResponse> {
    BatchResponse create (BatchRequest request);

    void markAsDelivered(Long batchId);
}
