package com.wms.wms.service.user;

import com.wms.wms.dto.request.user.UserCreateRequest;
import com.wms.wms.dto.response.User.UserGeneral;
import com.wms.wms.dto.response.User.UserInfoResponse;
import com.wms.wms.entity.CustomUserDetails;
import com.wms.wms.entity.User;
import com.wms.wms.entity.enumentity.base.UserRole;
import com.wms.wms.exception.ConstraintViolationException;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.user.UserCreateRequestMapper;
import com.wms.wms.mapper.user.UserGeneralMapper;
import com.wms.wms.mapper.user.UserInfoResponseMapper;
import com.wms.wms.repository.UserRepository;
import com.wms.wms.service.cache.RedisCacheService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisCacheService redisCacheService;

    @Override
    @Transactional
    public void create(UserCreateRequest request) {
        User newUser = UserCreateRequestMapper.INSTANCE.toEntity(request);

        newUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        userRepository.save(newUser);
        log.info("Service User - create user successfully");
    }

    @Override
    @Transactional
    public void update(UserCreateRequest request) {
        User newUser = UserCreateRequestMapper.INSTANCE.toEntity(request);

        newUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        userRepository.save(newUser);
        log.info("Service User - update user successfully");
    }

    @Override
    public List<UserInfoResponse> findAll() {
        List<User>  users = userRepository.findAll();
        log.info("Service User - Find all users successfully");
        return UserInfoResponseMapper.INSTANCE.toDtoList(users);
    }

    @Override
    public List<UserGeneral> findAllGeneral() {
        List<UserGeneral> userGeneralList = redisCacheService.getCachedListObject(RedisCacheService.ALL_USERS_KEY, UserGeneral.class);
        if (userGeneralList == null) {
            List<User> users = userRepository.findAll();
            userGeneralList = UserGeneralMapper.INSTANCE.toDtoList(users);
            redisCacheService.setCachedObjectWithDefaultExpired(RedisCacheService.ALL_USERS_KEY, userGeneralList);
        }
        return userGeneralList;
    }

    @Override
    public void deleteById(Long userId) {
        User user = getUserById(userId);

        if (user.getRole() == UserRole.ADMIN && userRepository.countByRole(UserRole.ADMIN).equals(1L)) {
            throw new ConstraintViolationException("Cannot delete last admin");
        }

        userRepository.delete(user);
        log.info("Service User - Delete user by Id: {} successfully", userId);
    }

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (principal instanceof CustomUserDetails customUserDetails) {
            return customUserDetails.getUser();
        }
        throw new IllegalStateException("No authenticated user found or principal is not of type CustomUserDetails");
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + userId));
    }
}
