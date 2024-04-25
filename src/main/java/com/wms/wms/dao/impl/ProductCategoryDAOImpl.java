package com.wms.wms.dao.impl;

import com.wms.wms.dao.IProductCategoryDAO;
import com.wms.wms.entity.ProductCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductCategoryDAOImpl implements IProductCategoryDAO {
    private final EntityManager entityManager;

    @Autowired
    public ProductCategoryDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<ProductCategory> findAll() {
        TypedQuery<ProductCategory> query = entityManager.createQuery("FROM ProductCategory", ProductCategory.class);
        return query.getResultList();
    }

    @Override
    public List<ProductCategory> findByName(String name) {
        TypedQuery<ProductCategory> query = entityManager.createQuery(
                "SELECT p FROM ProductCategory p WHERE p.name = :name", ProductCategory.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public ProductCategory findById(int id) {
        return entityManager.find(ProductCategory.class, id);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return entityManager.merge(productCategory);
    }

    @Override
    public void deleteById(ProductCategory productCategory) {
        entityManager.remove(productCategory);
    }
}
