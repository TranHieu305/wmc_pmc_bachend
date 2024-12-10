package com.wms.wms.mapper.product;

import com.wms.wms.dto.response.product.ProductResponse;
import com.wms.wms.entity.Product;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper extends BaseMapper<ProductResponse, Product> {
    ProductResponseMapper INSTANCE = Mappers.getMapper(ProductResponseMapper.class);
}
