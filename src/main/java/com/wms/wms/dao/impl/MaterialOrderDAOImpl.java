package com.wms.wms.dao.impl;

import com.wms.wms.dao.IMaterialOrderDAO;
import com.wms.wms.entity.MaterialOrder;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MaterialOrderDAOImpl implements IMaterialOrderDAO {
    private final EntityManager entityManager;

    @Autowired
    public MaterialOrderDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public MaterialOrder save(MaterialOrder theMaterialOrder) {
        MaterialOrder dbMaterialOrder = entityManager.merge(theMaterialOrder);
        return dbMaterialOrder;
    }

    @Override
    public MaterialOrder findById(int id) {
        MaterialOrder dbOrder = entityManager.find(MaterialOrder.class, id);
        return dbOrder;
    }
}
