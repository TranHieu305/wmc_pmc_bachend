package com.wms.wms.controller;

import com.wms.wms.dto.request.vrp.VrpRequest;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.service.vrpsolver.VrpSolverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vrp")
public class VrpController {
    private final VrpSolverService vrpSolverService;

    @GetMapping("/solve-vrp")
    public ResponseEntity<?> solveVrp(@RequestBody @Valid VrpRequest request) {
        log.info("Controller Vrp - Request solve vrp");
        vrpSolverService.solveVrp(
                request.getVehicleIds(),
                request.getVehicleCapacities(),
                request.getShipmentIds(),
                request.getShipmentWeights(),
                request.getDistanceMatrix()
        );
        return new ResponseSuccess(HttpStatus.OK, "Controller Vrp - Solve vrp successfully");
    }
}
