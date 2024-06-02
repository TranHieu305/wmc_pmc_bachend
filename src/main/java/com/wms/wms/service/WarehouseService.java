package com.wms.wms.service;

import com.wms.wms.dto.request.WarehouseRequest;
import com.wms.wms.dto.response.warehouse.WarehouseDetailResponse;
import com.wms.wms.entity.Warehouse;

import java.util.List;

public interface WarehouseService {

    // Get list of all warehouses
    List <WarehouseDetailResponse> findAll();

    // Get warehouse by id
    WarehouseDetailResponse findById(int id);

    // Save warehouse
    WarehouseDetailResponse save(WarehouseRequest request);

    // Get warehouse by ID
    Warehouse getWarehouseById(int warehouseId);

    // Delete warehouse by Id
    void deleteById(int id) ;
}
