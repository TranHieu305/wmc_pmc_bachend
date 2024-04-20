package com.wms.wms.mapper.warehouse;

import com.wms.wms.dto.response.WarehouseDetailResponse;
import com.wms.wms.entity.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WarehouseResponseMapper {
    WarehouseResponseMapper INSTANCE = Mappers.getMapper(WarehouseResponseMapper.class);
    WarehouseDetailResponse warehouseToResponse(Warehouse warehouse);
}
