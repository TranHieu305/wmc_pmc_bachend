//package com.wms.wms.repository;
//
//import com.wms.wms.entity.Product;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Set;
//
//@Repository
//public interface ProductRepository extends JpaRepository<Product, Integer> {
//
//    List<Product> findByIdIn(Set<Integer> ids);
//    List<Product> findByProductCategoryId(int categoryId);
//    List<Product> findByNameAndUom(String name, String uom);
//}
