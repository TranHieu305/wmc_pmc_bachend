package com.wms.wms.service.vehicle;

import com.wms.wms.dto.request.vehicle.VehicleRequest;
import com.wms.wms.dto.response.vehicle.VehicleResponse;
import com.wms.wms.entity.Vehicle;
import com.wms.wms.entity.enumentity.status.VehicleStatus;
import com.wms.wms.service.BaseService;

public interface VehicleService extends BaseService<VehicleRequest, VehicleResponse> {
    void changeStatus(Vehicle vehicle, VehicleStatus status);
}
