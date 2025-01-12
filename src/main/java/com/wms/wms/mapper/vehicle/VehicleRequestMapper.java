package com.wms.wms.mapper.vehicle;

import com.wms.wms.dto.request.vehicle.VehicleRequest;
import com.wms.wms.entity.Vehicle;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface VehicleRequestMapper extends BaseMapper<VehicleRequest, Vehicle> {
    VehicleRequestMapper INSTANCE = Mappers.getMapper(VehicleRequestMapper.class);
}
