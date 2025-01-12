package com.wms.wms.service.product;

import com.wms.wms.dto.request.product.ProductWarehouseHistoryRequest;
import com.wms.wms.dto.response.product.ProductWarehouseHistoryResponse;
import com.wms.wms.entity.*;
import com.wms.wms.service.BaseService;

import java.util.List;

public interface ProductWarehouseHistoryService extends
        BaseService<ProductWarehouseHistoryRequest, ProductWarehouseHistoryResponse> {
    void processProductWarehouseHistory(ProductWarehouseHistory pwh);

    void processImportBatchItems (Batch batch);

    void processExportBatchItems (Batch batch);

    void processImportBatchItem (BatchItem item, Warehouse warehouse);

    void processExportBatchItem (BatchItem item, Warehouse warehouse);

    void importProducedItem(ProducedItem producedItem);

    List <ProductWarehouseHistory> findByWarehouseId(Long warehouseId);
}
