package com.wms.wms.repository;

import com.wms.wms.entity.EntityFollowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityFollowingRepository extends JpaRepository<EntityFollowing, Long> {
    List<EntityFollowing> findAllByUserIdAndEntityName(Long userId, String entityName);
}
