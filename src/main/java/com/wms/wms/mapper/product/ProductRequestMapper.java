package com.wms.wms.mapper.product;

import com.wms.wms.dto.request.ProductRequestDTO;
import com.wms.wms.entity.Product;
import com.wms.wms.mapper.product.impl.ProductRequestMapperImpl;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = ProductRequestMapperImpl.class)
public interface ProductRequestMapper {
    ProductRequestMapper INSTANCE = Mappers.getMapper(ProductRequestMapper.class);

    Product requestToProduct(ProductRequestDTO productRequestDTO);
}
