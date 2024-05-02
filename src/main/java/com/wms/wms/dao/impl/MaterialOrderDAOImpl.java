package com.wms.wms.dao.impl;

import com.wms.wms.dao.AbstractDAO;
import com.wms.wms.dao.IMaterialOrderDAO;
import com.wms.wms.entity.MaterialOrder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MaterialOrderDAOImpl extends AbstractDAO<MaterialOrder> implements IMaterialOrderDAO {
    private EntityManager entityManager;

    @Autowired
    public MaterialOrderDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public MaterialOrder save(MaterialOrder theMaterialOrder) {
      return super.save(theMaterialOrder);
    }

    @Override
    public MaterialOrder findById(int id) {
        return super.findById(MaterialOrder.class, id);
    }

    @Override
    public List<MaterialOrder> findAll() {
       return super.findAll(MaterialOrder.class);
    }

    @Override
    public void delete(MaterialOrder materialOrder) {
        super.delete(materialOrder);
    }
}
