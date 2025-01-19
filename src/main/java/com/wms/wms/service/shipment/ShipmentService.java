package com.wms.wms.service.shipment;

import com.wms.wms.dto.request.shipment.ShipmentRequest;
import com.wms.wms.entity.Shipment;
import com.wms.wms.service.BaseService;

public interface ShipmentService extends BaseService<ShipmentRequest, Shipment> {
    void approve(Long shipmentId);

    void reject(Long shipmentId);

    void markAsInTransit(Long shipmentId);

    void markAsCompleted(Long shipmentId);
}
