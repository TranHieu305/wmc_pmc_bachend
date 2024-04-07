package com.wms.wms.dao.warehouse;

import com.wms.wms.entity.Warehouse;
import java.util.List;


public interface IWarehouseDAO {

    // Get list of all warehouses
    List<Warehouse> findAll();

   // Get warehouse by id
    Warehouse findById(int id);

    // Save warehouse
    Warehouse save(Warehouse theWarehouse);

    // Delete warehouse by Id
    void deleteById(int id);
}
