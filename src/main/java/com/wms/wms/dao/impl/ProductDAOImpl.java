package com.wms.wms.dao.impl;

import com.wms.wms.dao.AbstractDAO;
import com.wms.wms.dao.IProductDAO;
import com.wms.wms.entity.Product;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDAOImpl extends AbstractDAO<Product> implements IProductDAO {
    private EntityManager entityManager;

    @Autowired
    public ProductDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<Product> findAll() {
        return super.findAll(Product.class);
    }

    @Override
    public List<Product> findByName(String name) {
        String sql = "SELECT pc FROM Product pc WHERE pc.name = :name";
        Map<String, Object> params = Collections.singletonMap("name", name);
        return super.findMany(Product.class, sql, params);
    }

    @Override
    public List<Product> findByNameAndUom(String name, String uom) {
        String sql = "SELECT pc FROM Product pc WHERE pc.name = :name AND pc.uom = :uom";
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("uom", uom);
        return super.findMany(Product.class, sql, params);
    }

    @Override
    public Product findById(int id) {
        return super.findById(Product.class, id);
    }

    @Override
    public Product save(Product product) {
        return super.save(product);
    }

    @Override
    public void delete(Product product) {
        super.delete(product);
    }

    @Override
    public List<Product> findByCategoryId(int categoryId) {
        String sql = "SELECT pc FROM Product pc WHERE pc.category_id = :categoryId";
        Map<String, Object> params = Collections.singletonMap("category_id", categoryId);
        return super.findMany(Product.class, sql, params);
    }
}
