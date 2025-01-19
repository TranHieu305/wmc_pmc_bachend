package com.wms.wms.repository;

import com.wms.wms.entity.EntityParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityParticipantRepository extends JpaRepository<EntityParticipant, Long> {
}