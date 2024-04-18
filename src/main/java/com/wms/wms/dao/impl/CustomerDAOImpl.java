package com.wms.wms.dao.impl;

import com.wms.wms.dao.ICustomerDAO;
import com.wms.wms.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class CustomerDAOImpl implements ICustomerDAO {
    private EntityManager entityManager;

    public CustomerDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Customer> findAll() {
        TypedQuery<Customer> query = entityManager.createQuery("FROM Customer", Customer.class);
        List<Customer> customers = query.getResultList();
        return customers;
    }

    @Override
    public Customer findById(int id) {
        Customer theCustomer = entityManager.find(Customer.class, id);
        return theCustomer;
    }

    @Override
    public Customer save(Customer theCustomer) {
        Customer dbCustomer = entityManager.merge(theCustomer);
        entityManager.flush();
        entityManager.refresh(dbCustomer);
        return dbCustomer;
    }

    @Override
    public void deleteById(int id) {
        Customer theCustomer = entityManager.find(Customer.class, id);
        entityManager.remove(theCustomer);
    }
}
