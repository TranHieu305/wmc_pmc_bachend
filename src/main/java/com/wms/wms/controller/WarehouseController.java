package com.wms.wms.controller;

import com.wms.wms.dto.request.WarehouseRequest;
import com.wms.wms.dto.response.ResponseData;
import com.wms.wms.dto.response.warehouse.WarehouseDetailResponse;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.service.IWarehouseService;
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
@RequestMapping("/api")
public class WarehouseController {

    private final IWarehouseService warehouseService;

    private static final String ERROR_MESSAGE = "errorMessage={}";

    // Get list of all warehouses
    @GetMapping("/warehouses")
    public ResponseEntity<ResponseData> findAll() {
        log.info("Request get warehouse list");
        List<WarehouseDetailResponse> warehouses = warehouseService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get warehouses successfully", warehouses);
    }

    // Get warehouse detail by id
    @GetMapping("/warehouses/{warehouseId}")
    public ResponseEntity<ResponseData> findById(@Min(value = 0, message = "Id must be greater than 0")
                                                @PathVariable("warehouseId") int warehouseId) {
        log.info("Request get warehouseId={}", warehouseId);
        WarehouseDetailResponse response = warehouseService.findById(warehouseId);
        return new ResponseSuccess(HttpStatus.OK, "Get warehouse detail successfully", response);
    }

    @PostMapping("/warehouses")
    public ResponseEntity<ResponseData> addWarehouse(@RequestBody @Valid WarehouseRequest warehouseRequest) {
        log.info("Request add warehouse");
        WarehouseDetailResponse response = warehouseService.save(warehouseRequest);
        return new ResponseSuccess(HttpStatus.OK, "Add warehouses successfully", response);
    }

    @PutMapping("/warehouses/{warehouseId}")
    public ResponseEntity<ResponseData> updateWarehouse(@RequestBody @Valid WarehouseRequest warehouseRequest) {
        log.info("Request update warehouseId={}", warehouseRequest.getId());
        WarehouseDetailResponse response = warehouseService.save(warehouseRequest);
        return new ResponseSuccess(HttpStatus.OK, "Update warehouses successfully", response);
    }

    @DeleteMapping("/warehouses/{warehouseId}")
    public ResponseEntity<ResponseData> deleteById(@Min(value = 0, message = "Id must be greater than 0")
                                                   @PathVariable("warehouseId") int warehouseId) {

        log.info("Request delete warehouseId={}", warehouseId);
        warehouseService.deleteById(warehouseId);
        return new ResponseSuccess(HttpStatus.OK, "Delete warehouse successfully");
    }
}