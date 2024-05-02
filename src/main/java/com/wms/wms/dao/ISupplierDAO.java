package com.wms.wms.dao;

import com.wms.wms.entity.Supplier;

import java.util.List;

public interface ISupplierDAO {
    // Get list of all Suppliers
    List<Supplier> findAll();

    // Get Supplier by id
    Supplier findById(int id);

    // Save Supplier
    Supplier save(Supplier theSupplier);

    // Delete Supplier by Id
    void delete(Supplier supplier);

}
