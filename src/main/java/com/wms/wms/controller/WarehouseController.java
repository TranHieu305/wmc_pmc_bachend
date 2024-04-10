package com.wms.wms.controller;

import com.wms.wms.entity.Warehouse;
import com.wms.wms.exception.ObjectNotFoundException;
import com.wms.wms.response.ResponseSuccess;
import com.wms.wms.service.warehouse.IWarehouseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WarehouseController {

    private IWarehouseService iWarehouseService;

    public WarehouseController(IWarehouseService iWarehouseService) {
        this.iWarehouseService = iWarehouseService;
    }

    // Get list of all warehouses
    @GetMapping("/warehouses")
    public ResponseSuccess findAll() {
        List<Warehouse> warehouses = iWarehouseService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get warehouses successfully", warehouses);
    }

    @PostMapping("/warehouses")
    public ResponseSuccess addWarehouse(@RequestBody @Valid Warehouse warehouse) {
        warehouse.setId(0);
        Warehouse dbWarehouse = iWarehouseService.save(warehouse);

        return new ResponseSuccess(HttpStatus.CREATED, "Warehouses added successfully", dbWarehouse);
    }

    @PutMapping("/warehouses")
    public ResponseSuccess updateWarehouse(@RequestBody @Valid Warehouse theWarehouse) {
        Warehouse dbWarehouse = iWarehouseService.save(theWarehouse);
        return new ResponseSuccess(HttpStatus.ACCEPTED, "Warehouse updated successfully", dbWarehouse);
    }


    // Get warehouse by id
    @GetMapping("/warehouses/{warehouseId}")
    public ResponseSuccess findById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable int warehouseId)
            throws ObjectNotFoundException {

        Warehouse warehouse = iWarehouseService.findById(warehouseId);
        return new ResponseSuccess(HttpStatus.OK, "Get the warehouse id " + warehouseId + " successfully", warehouse);
    }

    @DeleteMapping("/warehouses/{warehouseId}")
    public ResponseSuccess deleteWarehouse(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable int warehouseId)
            throws ObjectNotFoundException{

        iWarehouseService.deleteById(warehouseId);
        return new ResponseSuccess(HttpStatus.NO_CONTENT, "Successfully deleted warehouse id: " + warehouseId);
    }
}
