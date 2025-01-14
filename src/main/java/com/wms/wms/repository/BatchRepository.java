package com.wms.wms.repository;

import com.wms.wms.entity.Batch;
import com.wms.wms.entity.enumentity.status.BatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {
    List<Batch> findByOrderId(Long orderId);
    List<Batch> findByStatus(BatchStatus status);

    List<Batch> findAllByOrderByCreatedAtDesc();

    List<Batch> findAllByIdInOrderByCreatedAtDesc(List<Long> ids);
}
