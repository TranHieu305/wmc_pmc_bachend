package com.wms.wms.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public class AuthResponse {
    @JsonProperty("access_token")
    private String accessToken;
}
