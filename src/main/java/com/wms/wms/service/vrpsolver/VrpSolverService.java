package com.wms.wms.service.vrpsolver;

import java.math.BigDecimal;

public interface VrpSolverService {
    String solveVrp (Long[] vehicleIds, BigDecimal[] vehicleCapacities,
                     Long[] shipmentIds, BigDecimal[] shipmentWeights,
                     BigDecimal[][] distanceMatrix);
}
