package com.wms.wms.repository;

import com.wms.wms.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findAllByOrderByCreatedAtDesc();

    List<Shipment> findAllByIdInOrderByCreatedAtDesc(List<Long> ids);
}
