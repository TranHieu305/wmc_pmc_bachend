package com.wms.wms.dao.impl;

import com.wms.wms.dao.AbstractDAO;
import com.wms.wms.dao.ISupplierDAO;
import com.wms.wms.entity.Supplier;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SupplierDAOImpl extends AbstractDAO<Supplier> implements ISupplierDAO {
    private EntityManager entityManager;
    public SupplierDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<Supplier> findAll() {
       return super.findAll(Supplier.class);
    }

    @Override
    public Supplier findById(int id) {
       return super.findById(Supplier.class, id);
    }

    @Override
    public Supplier save(Supplier theSupplier) {
      return super.save(theSupplier);
    }

    @Override
    public void delete(Supplier supplier) {
      super.delete(supplier);
    }
}
