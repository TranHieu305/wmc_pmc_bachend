package com.wms.wms.mapper.productcategory;

import com.wms.wms.dto.response.ProductCategoryDetailResponse;
import com.wms.wms.entity.ProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductCategoryResponseMapper {
    ProductCategoryResponseMapper INSTANCE = Mappers.getMapper(ProductCategoryResponseMapper.class);

    ProductCategoryDetailResponse categoryToResponse(ProductCategory productCategory);
}
