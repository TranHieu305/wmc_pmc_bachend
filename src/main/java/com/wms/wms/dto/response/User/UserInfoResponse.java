package com.wms.wms.dto.response.User;

import com.wms.wms.dto.response.BaseEntityResponse;
import com.wms.wms.entity.enumentity.UserRole;
import com.wms.wms.entity.enumentity.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoResponse extends BaseEntityResponse {
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private UserRole role;
    private UserStatus status;
}
