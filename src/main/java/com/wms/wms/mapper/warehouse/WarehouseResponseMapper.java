package com.wms.wms.mapper.warehouse;

import com.wms.wms.dto.response.WarehouseDetailResponse;
import com.wms.wms.entity.Warehouse;
import org.mapstruct.Mapper;

@Mapper
public interface WarehouseResponseMapper {
    WarehouseDetailResponse warehouseToResponse(Warehouse warehouse);
}
