package com.wms.wms.service.entityfollowing;

import com.wms.wms.entity.EntityFollowing;

import java.util.List;
import java.util.Set;

public interface EntityFollowingService {
    void addFollowingUsersToEntity(Set<Long> userIds, String entityName, Long entityID);

    List<EntityFollowing> getFollowings(Long userId, String entityName);
}
