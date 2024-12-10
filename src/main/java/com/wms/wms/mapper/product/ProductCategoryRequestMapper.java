package com.wms.wms.mapper.product;

import com.wms.wms.dto.request.product.ProductCategoryRequest;
import com.wms.wms.entity.ProductCategory;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductCategoryRequestMapper extends BaseMapper<ProductCategoryRequest, ProductCategory> {
    ProductCategoryRequestMapper INSTANCE = Mappers.getMapper(ProductCategoryRequestMapper.class);
}
