package com.wms.wms.service;

import com.wms.wms.entity.Supplier;
import com.wms.wms.exception.ResourceNotFoundException;

import java.util.List;

public interface ISupplierService {

    // Get list of all suppliers
    List<Supplier> findAll();

    // Get supplier by id
    Supplier findById(int id) throws ResourceNotFoundException;

    // Save supplier
    Supplier save(Supplier supplier);

    // Delete supplier by Id
    void deleteById(int id) throws ResourceNotFoundException;
}
