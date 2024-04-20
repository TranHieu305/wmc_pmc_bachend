package com.wms.wms.mapper.warehouse;

import com.wms.wms.dto.request.WarehouseRequestDTO;
import com.wms.wms.entity.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WarehouseRequestMapper {
    WarehouseRequestMapper INSTANCE = Mappers.getMapper(WarehouseRequestMapper.class);
    Warehouse requestToWarehouse(WarehouseRequestDTO warehouseRequestDTO);
}
