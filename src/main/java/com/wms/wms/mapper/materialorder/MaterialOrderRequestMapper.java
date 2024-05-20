package com.wms.wms.mapper.materialorder;

import com.wms.wms.dto.request.MaterialOrderRequestDTO;
import com.wms.wms.entity.MaterialOrder;
import com.wms.wms.mapper.materialorder.impl.MaterialOrderRequestMapperImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MaterialOrderRequestMapper {
    MaterialOrderRequestMapper INSTANCE = Mappers.getMapper( MaterialOrderRequestMapper.class);

    @Mapping(target = "orderItems", ignore = true)
    MaterialOrder requestToOrder(MaterialOrderRequestDTO materialOrderRequestDTO);
}
