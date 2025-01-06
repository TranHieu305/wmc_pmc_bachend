package com.wms.wms.repository;

import com.wms.wms.entity.User;
import com.wms.wms.entity.enumentity.base.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Long countByRole(UserRole role);
}
