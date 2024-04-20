package com.wms.wms.mapper.materialorder;

import com.wms.wms.dto.response.MaterialOrderResponse;
import com.wms.wms.entity.MaterialOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MaterialOrderResponseMapper {
    MaterialOrderResponseMapper INSTANCE = Mappers.getMapper(MaterialOrderResponseMapper.class);
    MaterialOrderResponse orderToResponse(MaterialOrder materialOrder);
}
