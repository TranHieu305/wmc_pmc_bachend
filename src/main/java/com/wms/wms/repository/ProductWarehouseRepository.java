package com.wms.wms.repository;

import com.wms.wms.entity.ProductWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductWarehouseRepository extends JpaRepository<ProductWarehouse, Long> {
    List<ProductWarehouse> findByWarehouseId(Long warehouseId);
    List<ProductWarehouse> findByProductId(Long productId);
    Optional<ProductWarehouse> findByWarehouseIdAndProductId(Long warehouseId, Long productId);

    List<ProductWarehouse> findByWarehouseIdAndProductIdIn(Long warehouseId, Set<Long> productIds);
}
