package com.wms.wms.mapper.product;

import com.wms.wms.dto.response.product.ProductCategoryResponse;
import com.wms.wms.entity.ProductCategory;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductCategoryResponseMapper extends BaseMapper<ProductCategoryResponse, ProductCategory> {
    ProductCategoryResponseMapper INSTANCE = Mappers.getMapper(ProductCategoryResponseMapper.class);
}
