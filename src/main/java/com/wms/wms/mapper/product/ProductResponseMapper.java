package com.wms.wms.mapper.product;

import com.wms.wms.dto.response.ProductDetailResponse;
import com.wms.wms.entity.Product;
import com.wms.wms.mapper.product.impl.ProductResponseMapperImpl;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = ProductResponseMapperImpl.class)
public interface ProductResponseMapper {
    ProductResponseMapper INSTANCE = Mappers.getMapper(ProductResponseMapper.class);

    ProductDetailResponse productToResponse(Product product);
}
