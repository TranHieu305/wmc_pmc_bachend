package com.wms.wms.repository;

import com.wms.wms.entity.ProductWarehouseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductWarehouseHistoryRepository extends JpaRepository<ProductWarehouseHistory, Long> {
}
