package com.wms.wms.service.cache;

import com.wms.wms.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class RedisDataInitializer implements CommandLineRunner {
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        //Cache general info of all users
        userService.findAllGeneral();
    }
}
