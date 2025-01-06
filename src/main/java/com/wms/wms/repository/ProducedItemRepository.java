package com.wms.wms.repository;

import com.wms.wms.entity.ProducedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProducedItemRepository extends JpaRepository<ProducedItem, Long> {
    List<ProducedItem> findByBatchIdOrderByCreatedAtDesc(Long batchId);
}
