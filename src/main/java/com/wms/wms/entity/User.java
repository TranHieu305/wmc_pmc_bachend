package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.BaseEntity;
import com.wms.wms.entity.enumentity.UserRole;
import com.wms.wms.entity.enumentity.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@Table(name = "user")
public class User extends BaseEntity {
    @Column(name = "user_name", unique = true, nullable = false)
    @NotBlank(message = "Username entity cannot be blank")
    @Size(min = 1, max = 255, message = "Username entity must be between 1 and 255 characters")
    private String username;

    @Column(name = "password_hash", nullable = false)
    @NotBlank(message = "Password hash cannot be blank")
    private String passwordHash;

    @Column(name = "full_name", nullable = false)
    @NotBlank(message = "User full name entity cannot be blank")
    @Size(min = 1, max = 255, message = "User full name entity must be between 1 and 255 characters")
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "User entity email cannot be blank")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.USER;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;
}
