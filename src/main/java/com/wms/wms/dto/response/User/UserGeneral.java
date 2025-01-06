package com.wms.wms.dto.response.User;

import com.wms.wms.entity.enumentity.base.UserRole;
import com.wms.wms.entity.enumentity.status.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class UserGeneral implements Serializable {
    private Long id;
    private String email;
    private UserRole role;
    private UserStatus status;
    private String username;
    private String fullName;
    private String phoneNumber;
}
