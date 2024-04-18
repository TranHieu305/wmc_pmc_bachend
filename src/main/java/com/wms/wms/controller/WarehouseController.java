package com.wms.wms.controller;

import com.wms.wms.dto.request.WarehouseRequestDTO;
import com.wms.wms.dto.response.ResponseData;
import com.wms.wms.dto.response.ResponseError;
import com.wms.wms.dto.response.WarehouseDetailResponse;
import com.wms.wms.entity.Warehouse;
import com.wms.wms.exception.ResourceNotFoundException;
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
@CrossOrigin(origins = { "http://localhost:3000", "https://phucthanhwms.netlify.app" })
@RequestMapping("/api")
public class WarehouseController {

    private final IWarehouseService warehouseService;

    private static final String ERROR_MESSAGE = "errorMessage={}";

    // Get list of all warehouses
    @GetMapping("/warehouses")
    public ResponseEntity<ResponseData> findAll() {
        log.info("Request get warehouse list");
        try {
            List<WarehouseDetailResponse> warehouses = warehouseService.findAll();
            return new ResponseSuccess(HttpStatus.OK, "Get warehouses successfully", warehouses);
        }
        catch (Exception exc) {
            log.error(ERROR_MESSAGE, exc.getMessage(), exc.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Get warehouse list fail");
        }
    }

    // Get warehouse detail by id
    @GetMapping("/warehouses/{warehouseId}")
    public ResponseEntity<ResponseData> findById(@Min(value = 0, message = "Id must be greater than 0") @PathVariable int warehouseId) {
        log.info("Request get warehouseId={}", warehouseId);
        try {
            WarehouseDetailResponse response = warehouseService.findById(warehouseId);
            return new ResponseSuccess(HttpStatus.OK, "Get warehouse detail successfully", response);
        }
        catch (Exception exc) {
            log.error(ERROR_MESSAGE, exc.getMessage(), exc.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Get warehouse detail fail");
        }
    }

    @PostMapping("/warehouses")
    public ResponseEntity<ResponseData> addWarehouse(@RequestBody @Valid WarehouseRequestDTO warehouseRequestDTO) {
        log.info("Request add warehouse");
        try {
            WarehouseDetailResponse response = warehouseService.save(warehouseRequestDTO);
            return new ResponseSuccess(HttpStatus.OK, "Add warehouses successfully", response);

        }
        catch (Exception exc) {
            log.error(ERROR_MESSAGE, exc.getMessage(), exc.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Add warehouse  fail");
        }
    }

    @PutMapping("/warehouses/{warehouseId}")
    public ResponseEntity<ResponseData> updateWarehouse(@RequestBody @Valid WarehouseRequestDTO warehouseRequestDTO,
                                                        @Min(value = 0, message = "Id must be greater than 0")
                                                        @PathVariable int warehouseId) {
        log.info("Request update warehouseId={}", warehouseId);
        try {
            WarehouseDetailResponse response = warehouseService.update(warehouseId, warehouseRequestDTO);
            return new ResponseSuccess(HttpStatus.OK, "Update warehouses successfully", response);
        }
        catch (Exception exc) {
            log.error(ERROR_MESSAGE, exc.getMessage(), exc.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Update warehouse  fail");
        }
    }

    @DeleteMapping("/warehouses/{warehouseId}")
    public ResponseEntity<ResponseData> deleteById(@Min(value = 0, message = "Id must be greater than 0")
                                                       @PathVariable int warehouseId) {

        log.info("Request delete warehouseId={}", warehouseId);
        try {
            warehouseService.deleteById(warehouseId);
            return new ResponseSuccess(HttpStatus.OK, "Delete warehouse successfully");
        }
        catch (Exception exc) {
            log.error(ERROR_MESSAGE, exc.getMessage(), exc.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Delete warehouse  fail");
        }
    }
}
