package com.wms.wms.mapper.productcategory;

import com.wms.wms.dto.request.ProductCategoryRequestDTO;
import com.wms.wms.entity.ProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductCategoryRequestMapper {
    ProductCategoryRequestMapper INSTANCE = Mappers.getMapper(ProductCategoryRequestMapper.class);

    @Mapping(target = "name", expression = "java(com.wms.wms.util.StringHelper.preProcess(requestDTO.getName()))")
    ProductCategory requestToCategory(ProductCategoryRequestDTO requestDTO);
}
