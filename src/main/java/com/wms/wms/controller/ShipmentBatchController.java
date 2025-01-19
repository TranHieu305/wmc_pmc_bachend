package com.wms.wms.controller;

import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.service.shipment.ShipmentBatchService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipment-batches")
public class ShipmentBatchController {
    private final ShipmentBatchService shipmentBatchService;

    @PatchMapping("/{shipmentBatchId}/status/delivered")
    public ResponseEntity<?> markAsDelivered(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("shipmentBatchId") Long shipmentBatchId) {
        log.info("Controller Shipment batch - Request to mark shipment batch as DELIVERED, Id={}", shipmentBatchId);
        shipmentBatchService.markAsDelivered(shipmentBatchId);
        return new ResponseSuccess(HttpStatus.OK, "Shipment batch marked as DELIVERED successfully");
    }
}
