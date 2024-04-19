package com.wms.wms.mapper.materialorder;

import com.wms.wms.dto.request.MaterialOrderRequestDTO;
import com.wms.wms.entity.MaterialOrder;
import org.mapstruct.Mapper;

@Mapper
public interface MaterialOrderRequestMapper {
    MaterialOrder requestToOrder(MaterialOrderRequestDTO materialOrderRequestDTO);
}
