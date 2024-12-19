package com.wms.wms.service.authentication;


import com.wms.wms.dto.request.auth.AuthRequest;
import com.wms.wms.dto.response.auth.AuthResponse;
import com.wms.wms.entity.CustomUserDetails;
import com.wms.wms.entity.User;
import com.wms.wms.entity.enumentity.UserRole;
import com.wms.wms.repository.UserRepository;
import com.wms.wms.service.jwt.JwtService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void initAdmin(){
        log.info("Post construct: Getting all users");
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            // Create admin
            log.info("Init first admin");

            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@gmail.com");
            admin.setRole(UserRole.ADMIN);
            admin.setPasswordHash(passwordEncoder.encode("admin"));
            admin.setFullName("admin");
            admin.setCreatedBy(0L);
            admin.setModifiedBy(0L);

            userRepository.save(admin);
            log.info("Init first admin successfully");
        }
    }
    @Override
    public AuthResponse authenticate(AuthRequest request) {
        // Authenticate the user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        log.info("Authenticate successfully");
        // Retrieve user for JWT generating
        User user = userRepository.findByEmail(request.getEmail());
        UserDetails userDetails = new CustomUserDetails(user);

        // Generate the JWT token
        String jwtToken = jwtService.generateToken(userDetails);
        return AuthResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .accessToken(jwtToken)
                .authorities(userDetails.getAuthorities().toString())
                .build();
    }
}
