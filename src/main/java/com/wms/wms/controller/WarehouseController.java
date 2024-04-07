package com.wms.wms.controller;

import com.wms.wms.entity.Warehouse;
import com.wms.wms.service.warehouse.IWarehouseService;
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
    public List<Warehouse> findAll() {
        return iWarehouseService.findAll();
    }

    // Get warehouse by id
    @GetMapping("/warehouses/{warehouseId}")
    public Warehouse findById(@PathVariable int warehouseId){
        Warehouse theWarehouse = iWarehouseService.findById(warehouseId);

        if (theWarehouse == null) {
            throw  new RuntimeException("Not found");
        }
        return  theWarehouse;
    }

    @PostMapping("/warehouses")
    public  Warehouse addWarehouse(@RequestBody Warehouse warehouse) {
        warehouse.setId(0);

        Warehouse dbWarehouse = iWarehouseService.save(warehouse);

        return dbWarehouse;
    }

}
