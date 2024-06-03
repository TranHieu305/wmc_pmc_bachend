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

    @Query("SELECT pp FROM ProductPrice pp WHERE pp.productId IN :productIds AND pp.dateApply = (" +
            "SELECT MAX(pp2.dateApply) FROM ProductPrice pp2 WHERE pp2.productId = pp.productId)")
    List<ProductPrice> findLatestPricesByProductIds(@Param("productIds") List<Long> productIds);

    @Query("SELECT pp FROM ProductPrice pp WHERE pp.id IN (" +
            "SELECT MAX(pp2.id) FROM ProductPrice pp2 WHERE pp2.dateApply = (" +
            "SELECT MAX(pp3.dateApply) FROM ProductPrice pp3 WHERE pp3.productId = pp2.productId) " +
            "GROUP BY pp2.productId)")
    List<ProductPrice> findLatestPricesForAllProducts();

    List<ProductPrice> findByProductId(int productId);
}
