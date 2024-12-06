//package com.wms.wms.repository;
//
//import com.wms.wms.entity.ProductPrice;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface ProductPriceRepository extends JpaRepository<ProductPrice, Integer> {
//
//    @Query(value = "SELECT pp.* FROM product_price pp " +
//            "INNER JOIN (" +
//            "    SELECT partner_id, MAX(date_apply) as max_date " +
//            "    FROM product_price " +
//            "    WHERE product_id = :productId " +
//            "    GROUP BY partner_id" +
//            ") latest " +
//            "ON pp.partner_id = latest.partner_id " +
//            "AND pp.date_apply = latest.max_date " +
//            "AND pp.product_id = :productId", nativeQuery = true)
//    List<ProductPrice> findLatestPricesByProductId(@Param("productId") int productId);
//
//    @Query(value = "SELECT pp.* FROM product_price pp " +
//            "INNER JOIN (" +
//            "    SELECT product_id, MAX(date_apply) as max_date " +
//            "    FROM product_price " +
//            "    WHERE partner_id = :partnerId " +
//            "    GROUP BY product_id" +
//            ") latest " +
//            "ON pp.product_id = latest.product_id " +
//            "AND pp.date_apply = latest.max_date " +
//            "AND pp.partner_id = :partnerId", nativeQuery = true)
//    List<ProductPrice> findLatestPricesByPartnerId(@Param("partnerId") int partnerId);
//
//    @Query("SELECT pp FROM ProductPrice pp WHERE pp.productId IN :productIds AND pp.dateApply = (" +
//            "SELECT MAX(pp2.dateApply) FROM ProductPrice pp2 WHERE pp2.productId = pp.productId) "+
//            "GROUP BY pp.productId")
//    List<ProductPrice> findLatestPricesByProductIds(@Param("productIds") List<Long> productIds);
//
//    @Query("SELECT pp FROM ProductPrice pp WHERE pp.id IN (" +
//            "SELECT MAX(pp2.id) FROM ProductPrice pp2 WHERE pp2.dateApply = (" +
//            "SELECT MAX(pp3.dateApply) FROM ProductPrice pp3 WHERE pp3.productId = pp2.productId) " +
//            "GROUP BY pp2.productId)")
//    List<ProductPrice> findLatestPricesForAllProducts();
//
//    @Query(value = "SELECT * FROM product_price WHERE product_id = :productId AND partner_id = :partnerId ORDER BY date_apply DESC LIMIT 1", nativeQuery = true)
//    ProductPrice findLatestByProductIdAndPartnerId(@Param("productId") int productId, @Param("partnerId") int partnerId);
//
//    @Query(value = "SELECT pp.* FROM product_price pp " +
//            "INNER JOIN (" +
//            "    SELECT product_id, partner_id, MAX(date_apply) as max_date " +
//            "    FROM product_price " +
//            "    GROUP BY product_id, partner_id" +
//            ") latest " +
//            "ON pp.product_id = latest.product_id " +
//            "AND pp.partner_id = latest.partner_id " +
//            "AND pp.date_apply = latest.max_date", nativeQuery = true)
//    List<ProductPrice> findAllCurrentPrices();
//
//    List<ProductPrice> findByProductId(int productId);
//}
