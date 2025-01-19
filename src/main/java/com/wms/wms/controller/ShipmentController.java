package com.wms.wms.controller;

import com.wms.wms.dto.request.shipment.ShipmentRequest;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.entity.Shipment;
import com.wms.wms.service.shipment.ShipmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipments")
public class ShipmentController {
    private final ShipmentService shipmentService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        log.info("Controller Shipment - Request get shipment list");
        List<Shipment> response = shipmentService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get shipment list successfully", response);
    }

    @GetMapping("/{shipmentId}")
    public ResponseEntity<?> findById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("shipmentId") Long shipmentId) {

        log.info("Controller Shipment - Get shipment detail id: {}", shipmentId);
        Shipment response = shipmentService.findById(shipmentId);
        return new ResponseSuccess(HttpStatus.OK, "Get shipment detail successfully", response);
    }

    @DeleteMapping("/{shipmentId}")
    public ResponseEntity<?> deleteById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("shipmentId") Long shipmentId) {

        log.info("Controller Shipment - Request delete shipment Id={}", shipmentId);
        shipmentService.deleteById(shipmentId);
        return new ResponseSuccess(HttpStatus.OK, "Delete shipment successfully");
    }

    @PostMapping("")
    public ResponseEntity<?> addShipment(@RequestBody @Valid ShipmentRequest requestDTO) {
        log.info("Controller Shipment - Request add shipment");
        Shipment response = shipmentService.save(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Create shipment successfully",response);
    }

    @PatchMapping("/{shipmentId}/approve")
    public ResponseEntity<?> approve(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("shipmentId") Long shipmentId) {
        log.info("Controller Shipment - Request to approve shipment Id={}", shipmentId);
        shipmentService.approve(shipmentId);
        return new ResponseSuccess(HttpStatus.OK, "Approved shipment successfully");
    }

    @PatchMapping("/{shipmentId}/reject")
    public ResponseEntity<?> reject(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("shipmentId") Long shipmentId) {
        log.info("Controller Shipment - Request to reject shipment Id={}", shipmentId);
        shipmentService.reject(shipmentId);
        return new ResponseSuccess(HttpStatus.OK, "Rejected shipment successfully");
    }

    @PatchMapping("/{shipmentId}/status/inTransit")
    public ResponseEntity<?> markAsInTransit(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("shipmentId") Long shipmentId) {
        log.info("Controller Shipment - Request to mark shipment as IN_TRANSIT, Id={}", shipmentId);
        shipmentService.markAsInTransit(shipmentId);
        return new ResponseSuccess(HttpStatus.OK, "Shipment marked as IN_TRANSIT successfully");
    }

    @PatchMapping("/{shipmentId}/status/completed")
    public ResponseEntity<?> markAsCompleted(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("shipmentId") Long shipmentId) {
        log.info("Controller Shipment - Request to mark shipment as COMPLETED, Id={}", shipmentId);
        shipmentService.markAsCompleted(shipmentId);
        return new ResponseSuccess(HttpStatus.OK, "Shipment marked as COMPLETED successfully");
    }
}
