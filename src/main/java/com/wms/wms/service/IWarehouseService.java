package com.wms.wms.service;

import com.wms.wms.dto.request.WarehouseRequestDTO;
import com.wms.wms.dto.response.WarehouseDetailResponse;
import com.wms.wms.entity.Warehouse;
import com.wms.wms.exception.ResourceNotFoundException;

import java.util.List;

public interface IWarehouseService {

    // Get list of all warehouses
    List <WarehouseDetailResponse> findAll();

    // Get warehouse by id
    WarehouseDetailResponse findById(int id);

    // Save warehouse
    WarehouseDetailResponse save(WarehouseRequestDTO request);

    // Update warehouse
    WarehouseDetailResponse update(int warehouseId, WarehouseRequestDTO request);

    // Delete warehouse by Id
    void deleteById(int id) ;
}
