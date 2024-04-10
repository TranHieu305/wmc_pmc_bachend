package com.wms.wms.controller;

import com.wms.wms.entity.Warehouse;
import com.wms.wms.exception.ObjectNotFoundException;
import com.wms.wms.service.warehouse.IWarehouseService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<Warehouse>> findAll() {
        return ResponseEntity.ok(iWarehouseService.findAll());
    }

    // Get warehouse by id
    @GetMapping("/warehouses/{warehouseId}")
    public ResponseEntity<Warehouse> findById(@PathVariable int warehouseId) throws ObjectNotFoundException {
        return  ResponseEntity.ok(iWarehouseService.findById(warehouseId));
    }

    @PostMapping("/warehouses")
    public ResponseEntity<Warehouse> addWarehouse(@RequestBody @Valid Warehouse warehouse) {
        warehouse.setId(0);

        Warehouse dbWarehouse = iWarehouseService.save(warehouse);

        return new  ResponseEntity<>(dbWarehouse, HttpStatus.CREATED);
    }

    @PutMapping("/warehouses")
    public ResponseEntity<Warehouse> updateWarehouse(@RequestBody Warehouse theWarehouse) {
        Warehouse dbWarehouse = iWarehouseService.save(theWarehouse);
        return ResponseEntity.ok(theWarehouse);
    }

    @DeleteMapping("/warehouses/{warehouseId}")
    public String deleteWarehouse(@PathVariable int warehouseId) throws ObjectNotFoundException{
        iWarehouseService.deleteById(warehouseId);
        return "Deleted";
    }
}
