package com.wms.wms.mapper.warehouse;

import com.wms.wms.dto.request.warehouse.WarehouseRequest;
import com.wms.wms.entity.Warehouse;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WarehouseRequestMapper extends BaseMapper<WarehouseRequest, Warehouse> {
    WarehouseRequestMapper INSTANCE = Mappers.getMapper(WarehouseRequestMapper.class);
}
