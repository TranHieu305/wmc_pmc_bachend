package com.wms.wms.service.product;

import com.wms.wms.dto.request.product.ProductWarehouseHistoryRequest;
import com.wms.wms.dto.response.product.ProductWarehouseHistoryResponse;
import com.wms.wms.entity.ProductWarehouseHistory;
import com.wms.wms.service.BaseService;

public interface ProductWarehouseHistoryService extends
        BaseService<ProductWarehouseHistoryRequest, ProductWarehouseHistoryResponse> {
    void processProductWarehouseHistory(ProductWarehouseHistory pwh);
}
