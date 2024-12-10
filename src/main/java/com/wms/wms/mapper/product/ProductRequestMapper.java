package com.wms.wms.mapper.product;

import com.wms.wms.dto.request.product.ProductRequest;
import com.wms.wms.entity.Product;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductRequestMapper extends BaseMapper<ProductRequest, Product> {
    ProductRequestMapper INSTANCE = Mappers.getMapper(ProductRequestMapper.class);
}
