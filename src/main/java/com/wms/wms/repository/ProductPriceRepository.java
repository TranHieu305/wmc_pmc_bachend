package com.wms.wms.repository;

import com.wms.wms.entity.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, Integer> {

    @Query(value = "SELECT * FROM product_price WHERE product_id = :productId ORDER BY start_date DESC LIMIT 1", nativeQuery = true)
    ProductPrice findLatestPriceByProductId(@Param("productId") int productId);

    List<ProductPrice> findByProductId(int productId);
}
