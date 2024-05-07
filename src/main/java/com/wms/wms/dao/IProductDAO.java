package com.wms.wms.dao;

import com.wms.wms.entity.Product;
import java.util.List;

public interface IProductDAO {
    // Get list of all Product
    List<Product> findAll();

    // Find by name
    List<Product> findByName(String name);

    List<Product> findByNameAndUom(String name, String uom);

    // Get Product by id
    Product findById(int id);

    // Save Product
    Product save(Product product);


    // Delete Product
    void delete(Product product);
}
