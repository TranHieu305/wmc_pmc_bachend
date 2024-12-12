package com.wms.wms.service.product;

import com.wms.wms.dto.response.product.ProductWarehouseResponse;
import com.wms.wms.entity.ProductWarehouse;
import com.wms.wms.service.BaseService;

import java.util.List;


public interface ProductWarehouseService extends BaseService<ProductWarehouse, ProductWarehouseResponse> {
    List<ProductWarehouseResponse> findByWarehouseId(Long warehouseId);
    List<ProductWarehouseResponse> findByProductId(Long productId);
}
