package com.wms.wms.service;

import com.wms.wms.entity.Customer;
import com.wms.wms.exception.ObjectNotFoundException;

import java.util.List;

public interface ICustomerService {
    // Get list of all Customers
    List<Customer> findAll();

    // Get Customer by id
    Customer findById(int id) throws ObjectNotFoundException;

    // Save Customer
    Customer save(Customer warehouse);

    // Delete Customer by Id
    void deleteById(int id) throws ObjectNotFoundException;
}
