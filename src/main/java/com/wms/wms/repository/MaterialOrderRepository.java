package com.wms.wms.repository;

import com.wms.wms.entity.MaterialOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialOrderRepository extends JpaRepository<MaterialOrder, Integer> {
}
