package com.wms.wms.dao;

import com.wms.wms.entity.ProductCategory;

import java.util.List;

public interface IProductCategoryDAO {
    // Get list of all ProductCategories
    List<ProductCategory> findAll();

    // Find by name
    List<ProductCategory> findByName(String name);

    // Get ProductCategory by id
    ProductCategory findById(int id);

    // Save ProductCategory
    ProductCategory save(ProductCategory productCategory);


    // Delete ProductCategory
    void deleteById(ProductCategory productCategory);
}
