package com.wms.wms.service.impl;

import com.wms.wms.dao.customer.ICustomerDAO;
import com.wms.wms.entity.Customer;
import com.wms.wms.exception.ObjectNotFoundException;
import com.wms.wms.service.ICustomerService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {
    private ICustomerDAO customerDAO;

    public CustomerServiceImpl(ICustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public List<Customer> findAll() {
        return customerDAO.findAll();
    }

    @Override
    public Customer findById(int id) throws ObjectNotFoundException {
        Customer customer = customerDAO.findById(id);
        if (customer == null) {
            throw new ObjectNotFoundException("Customer not found with id: " + id);
        }
        return customer;
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        return customerDAO.save(customer);
    }

    @Override
    @Transactional
    public void deleteById(int id) throws ObjectNotFoundException {
        Customer customer = customerDAO.findById(id);
        if (customer == null) {
            throw new ObjectNotFoundException("Customer not found with id: " + id);
        }

        customerDAO.deleteById(id);
    }
}
