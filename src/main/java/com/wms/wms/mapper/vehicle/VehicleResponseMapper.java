package com.wms.wms.mapper.vehicle;

import com.wms.wms.dto.response.vehicle.VehicleResponse;
import com.wms.wms.entity.Vehicle;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VehicleResponseMapper extends BaseMapper<VehicleResponse, Vehicle> {
    VehicleResponseMapper INSTANCE = Mappers.getMapper(VehicleResponseMapper.class);
}
