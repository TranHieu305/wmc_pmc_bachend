package com.wms.wms.service.shipment;

import com.wms.wms.entity.Shipment;

public interface ShipmentBatchService {
    void markAsDelivered(Long shipmentBatchId);

    void processInTransitShipment(Shipment shipment);
}
