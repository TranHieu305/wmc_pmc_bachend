package com.wms.wms.dao.impl;

import com.wms.wms.dao.IMaterialOrderDAO;
import com.wms.wms.entity.MaterialOrder;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MaterialOrderImpl implements IMaterialOrderDAO {
    private EntityManager entityManager;

    @Autowired
    public MaterialOrderImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public MaterialOrder save(MaterialOrder theMaterialOrder) {
        MaterialOrder dbMaterialOrder = entityManager.merge(theMaterialOrder);
        return dbMaterialOrder;
    }
}
