package com.wms.wms.repository;

import com.wms.wms.entity.PartnerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerAddressRepository extends JpaRepository<PartnerAddress, Long> {
}
