package com.wms.wms.mapper.product;

import com.wms.wms.dto.request.product.ProductWarehouseHistoryRequest;
import com.wms.wms.entity.ProductWarehouseHistory;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductWarehouseHistoryRequestMapper extends BaseMapper<ProductWarehouseHistoryRequest, ProductWarehouseHistory> {
    ProductWarehouseHistoryRequestMapper INSTANCE = Mappers.getMapper(ProductWarehouseHistoryRequestMapper.class);
}
