package com.wms.wms.controller;

import com.wms.wms.dto.request.auth.AuthRequest;
import com.wms.wms.dto.response.ResponseData;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.dto.response.auth.AuthResponse;
import com.wms.wms.service.authentication.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ResponseData> login(@RequestBody @Valid AuthRequest request) {
        log.info("Request login");
        AuthResponse authResponse = authenticationService.authenticate(request);
        return new ResponseSuccess(HttpStatus.OK, "Login successfully",authResponse);
    }
}
