package com.wms.wms.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class AuthResponse {
    @JsonProperty("access_token")
    private String accessToken;
}
