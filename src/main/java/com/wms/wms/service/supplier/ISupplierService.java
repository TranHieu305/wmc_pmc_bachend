package com.wms.wms.service.supplier;

import com.wms.wms.entity.Supplier;
import com.wms.wms.exception.ObjectNotFoundException;

import java.util.List;

public interface ISupplierService {

    // Get list of all suppliers
    List<Supplier> findAll();

    // Get supplier by id
    Supplier findById(int id) throws ObjectNotFoundException;

    // Save supplier
    Supplier save(Supplier supplier);

    // Delete supplier by Id
    void deleteById(int id) throws ObjectNotFoundException;
}
