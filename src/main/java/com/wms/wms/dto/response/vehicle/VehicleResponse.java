package com.wms.wms.dto.response.vehicle;

import com.wms.wms.dto.response.BaseEntityResponse;
import com.wms.wms.entity.enumentity.status.VehicleStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class VehicleResponse extends BaseEntityResponse {
    private String name;
    private String description;
    private String licensePlate;
    private BigDecimal loadCapacity;
    private VehicleStatus status;
}
