package com.wms.wms.repository;

import com.wms.wms.entity.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LotRepository extends JpaRepository<Lot, Integer> {
}
