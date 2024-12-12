package com.wms.wms.mapper.product;

import com.wms.wms.dto.response.product.ProductWarehouseHistoryResponse;
import com.wms.wms.entity.ProductWarehouseHistory;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductWarehouseHistoryResponseMapper extends BaseMapper<ProductWarehouseHistoryResponse, ProductWarehouseHistory> {
    ProductWarehouseHistoryResponseMapper INSTANCE =
            Mappers.getMapper(ProductWarehouseHistoryResponseMapper.class);
}
