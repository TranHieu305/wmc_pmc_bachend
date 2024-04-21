package com.wms.wms.mapper.materialorder;

import com.wms.wms.dto.response.MaterialOrderResponse;
import com.wms.wms.entity.MaterialOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MaterialOrderResponseMapper {
    MaterialOrderResponseMapper INSTANCE = Mappers.getMapper(MaterialOrderResponseMapper.class);
    @Mapping(target = "orderItems", ignore = true)
    MaterialOrderResponse orderToResponse(MaterialOrder materialOrder);
}
