package com.wms.wms.controller;

import com.wms.wms.dto.request.user.UserCreateRequest;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.dto.response.User.UserInfoResponse;
import com.wms.wms.service.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserCreateRequest request) {
        log.info("Request create user");
        request.setId(0L);
        userService.create(request);
        return new ResponseSuccess(HttpStatus.OK, "Add user successfully");
    }

    @PutMapping("")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserCreateRequest request) {
        log.info("Request update user");
        userService.update(request);
        return new ResponseSuccess(HttpStatus.OK, "Update user successfully");
    }

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        log.info("Request get user list");
        List<UserInfoResponse> responses = userService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get all users successfully", responses);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteById(@Min(value = 0, message = "Id must be greater than 0")
                                        @PathVariable("userId") Long userId) {

        log.info("Request delete userId={}", userId);
        userService.deleteById(userId);
        return new ResponseSuccess(HttpStatus.OK, "Delete user successfully");
    }
}
