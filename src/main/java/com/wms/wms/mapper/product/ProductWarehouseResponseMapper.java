package com.wms.wms.mapper.product;

import com.wms.wms.dto.response.product.ProductWarehouseResponse;
import com.wms.wms.entity.ProductWarehouse;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductWarehouseResponseMapper extends BaseMapper<ProductWarehouseResponse, ProductWarehouse> {
    ProductWarehouseResponseMapper INSTANCE = Mappers.getMapper(ProductWarehouseResponseMapper.class);
}
