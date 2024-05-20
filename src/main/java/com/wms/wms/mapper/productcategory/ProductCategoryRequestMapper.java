package com.wms.wms.mapper.productcategory;

import com.wms.wms.dto.request.ProductCategoryRequestDTO;
import com.wms.wms.entity.ProductCategory;
import com.wms.wms.util.StringHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = StringHelper.class)
public interface ProductCategoryRequestMapper {
    ProductCategoryRequestMapper INSTANCE = Mappers.getMapper(ProductCategoryRequestMapper.class);

    @Mapping(target = "name", source = "name", qualifiedByName = "preProcessString")
    ProductCategory requestToCategory(ProductCategoryRequestDTO requestDTO);
}
