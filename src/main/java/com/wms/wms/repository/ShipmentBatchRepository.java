package com.wms.wms.repository;

import com.wms.wms.entity.ShipmentBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentBatchRepository extends JpaRepository<ShipmentBatch, Long> {
    List<ShipmentBatch> findAllByPartnerAddressId(Long addressId);
}
