package com.wms.wms.mapper.warehouse;

import com.wms.wms.dto.request.WarehouseRequestDTO;
import com.wms.wms.entity.Warehouse;
import org.mapstruct.Mapper;

@Mapper
public interface WarehouseRequestMapper {
    Warehouse requestToWarehouse(WarehouseRequestDTO warehouseRequestDTO);
}
