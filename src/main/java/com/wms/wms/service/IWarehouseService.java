package com.wms.wms.service;

import com.wms.wms.entity.Warehouse;
import com.wms.wms.exception.ObjectNotFoundException;

import java.util.List;

public interface IWarehouseService {

    // Get list of all warehouses
    List <Warehouse> findAll();

    // Get warehouse by id
    Warehouse findById(int id) throws ObjectNotFoundException;

    // Save warehouse
    Warehouse save(Warehouse warehouse);

    // Delete warehouse by Id
    void deleteById(int id) throws ObjectNotFoundException;
}
