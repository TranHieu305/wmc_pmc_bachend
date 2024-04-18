package com.wms.wms.controller;

import com.wms.wms.entity.Supplier;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.service.ISupplierService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "https://phucthanhwms.netlify.app" })
@RequestMapping("/api")
public class SupplierController {

    private ISupplierService iSupplierService;

    public SupplierController(ISupplierService iSupplierService) {
        this.iSupplierService = iSupplierService;
    }

    // Get list of all suppliers
    @GetMapping("/suppliers")
    public ResponseSuccess findAll() {
        List<Supplier> suppliers = iSupplierService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get suppliers successfully", suppliers);
    }

    @PostMapping("/suppliers")
    public ResponseSuccess addWarehouse(@RequestBody @Valid Supplier supplier) {
        supplier.setId(0);
        Supplier dbSupplier = iSupplierService.save(supplier);

        return new ResponseSuccess(HttpStatus.CREATED, "Supplier added successfully", dbSupplier);
    }

    @PutMapping("/suppliers")
    public ResponseSuccess updateWarehouse(@RequestBody @Valid Supplier theSupplier) {
        Supplier dbSupplier = iSupplierService.save(theSupplier);
        return new ResponseSuccess(HttpStatus.ACCEPTED, "Supplier updated successfully", dbSupplier);
    }

    // Get supplier by id
    @GetMapping("/suppliers/{supplierId}")
    public ResponseSuccess findById(
            @Min(value = 1, message = "Id must be greater than 0") @PathVariable int supplierId)
            throws ResourceNotFoundException {

        Supplier supplier = iSupplierService.findById(supplierId);
        return new ResponseSuccess(HttpStatus.OK, "Get the supplier id " + supplierId + " successfully", supplier);
    }

    @DeleteMapping("/suppliers/{supplierId}")
    public ResponseSuccess deleteById(
            @Min(value = 1, message = "Id must be greater than 0") @PathVariable int supplierId)
            throws ResourceNotFoundException {

        iSupplierService.deleteById(supplierId);
        return new ResponseSuccess(HttpStatus.NO_CONTENT, "Successfully deleted supplier id: " + supplierId);
    }
}
