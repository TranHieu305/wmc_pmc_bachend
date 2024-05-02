package com.wms.wms.dao.impl;

import com.wms.wms.dao.AbstractDAO;
import com.wms.wms.dao.IWarehouseDAO;
import com.wms.wms.entity.Warehouse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WarehouseDAOImpl extends AbstractDAO<Warehouse> implements IWarehouseDAO {
    private EntityManager entityManager;

    @Autowired
    public WarehouseDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }


    @Override
    public List<Warehouse> findAll() {
        return super.findAll(Warehouse.class);
    }

    @Override
    public Warehouse findById(int id) {
        return super.findById(Warehouse.class, id);
    }

    @Override
    public Warehouse save(Warehouse theWarehouse) {
      return super.save(theWarehouse);
    }

    @Override
    public void deleteById(Warehouse warehouse) {
        super.delete(warehouse);
    }
}
