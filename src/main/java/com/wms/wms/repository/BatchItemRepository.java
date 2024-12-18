package com.wms.wms.repository;

import com.wms.wms.entity.BatchItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchItemRepository extends JpaRepository<BatchItem, Long> {
}
