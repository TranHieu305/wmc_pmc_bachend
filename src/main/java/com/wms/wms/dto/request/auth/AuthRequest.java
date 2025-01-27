package com.wms.wms.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotBlank(message = "Email auth request cannot be blank")
    private String email;

    @NotBlank(message = "Password auth request cannot be blank")
    private String password;
}
