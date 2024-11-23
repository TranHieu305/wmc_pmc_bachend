package com.wms.wms.service.user;

import com.wms.wms.entity.User;
import com.wms.wms.entity.CustomUserDetails;
import com.wms.wms.exception.AuthenticationServiceException;
import com.wms.wms.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Authenticating: Load user by username: " + username);
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new AuthenticationServiceException("Authentication failed for user: " + username);
        }
        return new CustomUserDetails(user);
    }
}
