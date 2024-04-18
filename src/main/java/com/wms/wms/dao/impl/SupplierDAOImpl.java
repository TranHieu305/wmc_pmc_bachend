package com.wms.wms.dao.impl;

import com.wms.wms.dao.ISupplierDAO;
import com.wms.wms.entity.Supplier;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SupplierDAOImpl implements ISupplierDAO {
    private EntityManager entityManager;
    public SupplierDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Supplier> findAll() {
        TypedQuery<Supplier> query = entityManager.createQuery("FROM Supplier", Supplier.class);
        List<Supplier> suppliers = query.getResultList();
        return suppliers;
    }

    @Override
    public Supplier findById(int id) {
        Supplier theSupplier = entityManager.find(Supplier.class, id);
        return theSupplier;
    }

    @Override
    public Supplier save(Supplier theSupplier) {
        Supplier dbSupplier = entityManager.merge(theSupplier);
        entityManager.flush();
        entityManager.refresh(dbSupplier);
        return dbSupplier;
    }

    @Override
    public void deleteById(int id) {
        Supplier theSupplier = entityManager.find(Supplier.class, id);
        entityManager.remove(theSupplier);
    }
}
