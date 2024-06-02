package com.wms.wms.controller;

import com.wms.wms.dto.request.SupplierRequest;
import com.wms.wms.dto.response.*;
import com.wms.wms.dto.response.supplier.SupplierDetailResponse;
import com.wms.wms.service.SupplierService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
public class SupplierController {

    private final SupplierService supplierService;
    private static final String ERROR_MESSAGE = "errorMessage={}";

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    // Get list of all suppliers
    @GetMapping("/suppliers")
    public ResponseEntity<ResponseData> findAll() {
        log.info("Request get supplier list");
        List<SupplierDetailResponse> response = supplierService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get supplier list successfully", response);
    }

    @PostMapping("/suppliers")
    public ResponseEntity<ResponseData> addSupplier(@RequestBody @Valid SupplierRequest requestDTO) {
        log.info("Request add supplier");
        requestDTO.setId(0);
        SupplierDetailResponse response = supplierService.save(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Create supplier successfully",response);
    }

    @PutMapping("/suppliers")
    public ResponseEntity<ResponseData> updateSupplier(@RequestBody @Valid SupplierRequest requestDTO) {
        log.info("Request update supplier id: {}", requestDTO.getId());
        SupplierDetailResponse response = supplierService.save(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Update supplier successfully",response);
    }

    // Get supplier by id
    @GetMapping("/suppliers/{supplierId}")
    public ResponseEntity<ResponseData> findById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("supplierId") int supplierId) {

        log.info("Get Supplier detail id: {}", supplierId);
        SupplierDetailResponse response = supplierService.findById(supplierId);
        return new ResponseSuccess(HttpStatus.OK, "Get Supplier detail successfully", response);

    }

    @DeleteMapping("/suppliers/{supplierId}")
    public ResponseEntity<ResponseData> deleteById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("supplierId") int supplierId) {

        log.info("Request delete Supplier Id={}", supplierId);
        supplierService.deleteById(supplierId);
        return new ResponseSuccess(HttpStatus.OK, "Delete Supplier successfully");
    }
}
