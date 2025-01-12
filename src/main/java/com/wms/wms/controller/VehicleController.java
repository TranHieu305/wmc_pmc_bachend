package com.wms.wms.controller;

import com.wms.wms.dto.request.vehicle.VehicleRequest;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.dto.response.vehicle.VehicleResponse;
import com.wms.wms.service.vehicle.VehicleService;
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
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        log.info("Controller Vehicle - Request get vehicle list");
        List<VehicleResponse> response = vehicleService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Controller Vehicle - Get vehicle list successfully", response);
    }

    @PostMapping("")
    public ResponseEntity<?> addVehicle(@RequestBody @Valid VehicleRequest requestDTO) {
        log.info("Controller Vehicle - Request add vehicle");
        requestDTO.setId(0L);
        VehicleResponse response = vehicleService.save(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Controller Vehicle - Create vehicle successfully",response);
    }

    @PutMapping("")
    public ResponseEntity<?> updateVehicle(@RequestBody @Valid VehicleRequest requestDTO) {
        log.info("Controller Vehicle - Request update vehicle id: {}", requestDTO.getId());
        VehicleResponse response = vehicleService.save(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Controller Vehicle - Update vehicle successfully",response);
    }

    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<?> deleteById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("vehicleId") Long vehicleId) {

        log.info("Controller Vehicle - Request delete vehicle Id={}", vehicleId);
        vehicleService.deleteById(vehicleId);
        return new ResponseSuccess(HttpStatus.OK, "Controller Vehicle - Delete vehicle successfully");
    }
}
