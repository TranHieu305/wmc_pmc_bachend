package com.wms.wms.dto.response.auth;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private String accessToken;

    private String email;

    private Long userId;

    private String authorities;
}
