package com.wms.wms.mapper.materialorder;

import com.wms.wms.dto.response.MaterialOrderResponse;
import com.wms.wms.entity.MaterialOrder;
import org.mapstruct.Mapper;

@Mapper
public interface MaterialOrderResponseMapper {
    MaterialOrderResponse orderToResponse(MaterialOrder materialOrder);
}
