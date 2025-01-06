package com.wms.wms.dto.request.user;

import com.wms.wms.entity.enumentity.base.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateRequest {
    private Long id;

    @NotBlank(message = "Username entity cannot be blank")
    @Size(min = 1, max = 255, message = "Username entity must be between 1 and 255 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotBlank(message = "User full name entity cannot be blank")
    @Size(min = 1, max = 255, message = "User full name entity must be between 1 and 255 characters")
    private String fullName;

    private String email;

    private String phoneNumber;

    private UserRole role;
}
