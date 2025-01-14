package com.wms.wms.dto.request.shipment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentBatchRequest {
    private Long batchId;
    private Long partnerAddressId;
    private Long shipmentOrder;
}
