package com.wms.wms.service.user;

import com.wms.wms.entity.User;
import com.wms.wms.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initAdmin(){
        log.info("Getting all users");
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            // Create admin
            log.info("Init first admin");
            User admin = User.builder()
                    .username("admin")
                    .email("admin@gmail.com")
                    .role(1)
                    .passwordHash(passwordEncoder.encode("admin"))
                    .fullName("admin")
                    .build();


        }
    }
}
