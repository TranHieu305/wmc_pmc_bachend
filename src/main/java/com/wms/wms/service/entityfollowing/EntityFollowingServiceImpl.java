package com.wms.wms.service.entityfollowing;

import com.wms.wms.entity.EntityFollowing;
import com.wms.wms.repository.EntityFollowingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class EntityFollowingServiceImpl implements EntityFollowingService {
    private final EntityFollowingRepository entityFollowingRepository;

    @Override
    @Transactional
    public void addFollowingUsersToEntity(Set<Long> userIds, String entityName, Long entityID) {
        List<EntityFollowing> followings = userIds.stream().map(userId ->
                (EntityFollowing) EntityFollowing.builder()
                .userId(userId)
                .entityName(entityName)
                .entityId(entityID)
                .build()).toList();

        entityFollowingRepository.saveAll(followings);
        log.info("Service Entity following - save followings successfully");
    }

    @Override
    public List<EntityFollowing> getFollowings(Long userId, String entityName) {
        return entityFollowingRepository.findAllByUserIdAndEntityName(userId, entityName);
    }
}
