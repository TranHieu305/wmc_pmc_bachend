package com.wms.wms.repository;

import com.wms.wms.entity.Batch;
import com.wms.wms.entity.BatchItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchItemRepository extends JpaRepository<BatchItem, Long> {
    List<BatchItem> findByOrderItemId(Long orderItemId);

    List<BatchItem> findByProductId(Long productId);
}
