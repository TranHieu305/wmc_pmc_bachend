package com.wms.wms.dao.customer;

import com.wms.wms.entity.Customer;

import java.util.List;

public interface ICustomerDAO {
    // Get list of all customers
    List<Customer> findAll();

    // Get customer by id
    Customer findById(int id);

    // Save customer
    Customer save(Customer theCustomer);

    // Delete customer by Id
    void deleteById(int id);
}
