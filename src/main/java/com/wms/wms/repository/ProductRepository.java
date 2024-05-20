package com.wms.wms.repository;

import com.wms.wms.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByProductCategoryId(int categoryId);
    List<Product> findByNameAndUom(String name, String uom);
}
