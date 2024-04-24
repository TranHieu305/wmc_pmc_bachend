package com.wms.wms.controller;

import com.wms.wms.dto.request.SupplierRequestDTO;
import com.wms.wms.dto.response.*;
import com.wms.wms.entity.Supplier;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.service.ISupplierService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "https://phucthanhwms.netlify.app" })
@Slf4j
@RequestMapping("/api")
public class SupplierController {

    private final ISupplierService supplierService;
    private static final String ERROR_MESSAGE = "errorMessage={}";

    public SupplierController(ISupplierService supplierService) {
        this.supplierService = supplierService;
    }

    // Get list of all suppliers
    @GetMapping("/suppliers")
    public ResponseEntity<ResponseData> findAll() {
        log.info("Request get supplier list");
        try {
            List<SupplierDetailResponse> response = supplierService.findAll();
            return new ResponseSuccess(HttpStatus.OK, "Get supplier list successfully", response);
        }
        catch (Exception exc) {
            log.error(ERROR_MESSAGE, exc.getMessage(), exc.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Get supplier list fail");
        }
    }

    @PostMapping("/suppliers")
    public ResponseEntity<ResponseData> addSupplier(@RequestBody @Valid SupplierRequestDTO requestDTO) {
        log.info("Request add supplier");
        try {
            requestDTO.setId(0);
            SupplierDetailResponse response = supplierService.save(requestDTO);
            return new ResponseSuccess(HttpStatus.OK, "Create supplier successfully",response);
        }
        catch (Exception exception) {
            log.error(ERROR_MESSAGE, exception.getMessage(), exception.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Create supplier fail");
        }
    }

    @PutMapping("/suppliers")
    public ResponseEntity<ResponseData> updateSupplier(@RequestBody @Valid SupplierRequestDTO requestDTO) {
        log.info("Request add supplier");
        try {
            SupplierDetailResponse response = supplierService.save(requestDTO);
            return new ResponseSuccess(HttpStatus.OK, "Update supplier successfully",response);
        }
        catch (Exception exception) {
            log.error(ERROR_MESSAGE, exception.getMessage(), exception.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Update supplier fail");
        }
    }

    // Get supplier by id
    @GetMapping("/suppliers/{supplierId}")
    public ResponseEntity<ResponseData> findById(
            @Min(value = 1, message = "Id must be greater than 0") @PathVariable int supplierId) {

        log.info("Get Supplier detail id: {}", supplierId);
        try {
            SupplierDetailResponse response = supplierService.findById(supplierId);
            return new ResponseSuccess(HttpStatus.OK, "Get Supplier detail successfully", response);
        }
        catch (Exception exception) {
            log.error(ERROR_MESSAGE, exception.getMessage(), exception.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Get Supplier detail fail");
        }
    }

    @DeleteMapping("/suppliers/{supplierId}")
    public ResponseEntity<ResponseData> deleteById(
            @Min(value = 1, message = "Id must be greater than 0") @PathVariable int supplierId) {

        log.info("Request delete Supplier Id={}", supplierId);
        try {
            supplierService.deleteById(supplierId);
            return new ResponseSuccess(HttpStatus.OK, "Delete Supplier successfully");
        }
        catch (Exception exc) {
            log.error(ERROR_MESSAGE, exc.getMessage(), exc.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Delete Supplier fail");
        }
    }
}
