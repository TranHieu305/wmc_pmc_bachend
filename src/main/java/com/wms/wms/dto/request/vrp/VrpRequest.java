package com.wms.wms.dto.request.vrp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class VrpRequest {
    private Long[] vehicleIds;
    private BigDecimal[] vehicleCapacities;
    private Long[] shipmentIds;
    private java.math.BigDecimal[] shipmentWeights;
    private BigDecimal[][] distanceMatrix;
}
