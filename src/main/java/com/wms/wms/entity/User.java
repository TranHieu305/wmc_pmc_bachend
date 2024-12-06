package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.BaseEntity;
import com.wms.wms.entity.enumentity.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Column(name = "user_name")
    @NotBlank(message = "Username entity cannot be blank")
    @Size(min = 1, max = 255, message = "Username entity must be between 1 and 255 characters")
    private String username;

    @Column(name = "password_hash")
    @NotBlank(message = "Password hash cannot be blank")
    private String passwordHash;

    @Column(name = "full_name")
    @NotBlank(message = "User full name entity cannot be blank")
    @Size(min = 1, max = 255, message = "User full name entity must be between 1 and 255 characters")
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "role")
    private int role;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;
}
