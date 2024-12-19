package com.wms.wms.service.user;

import com.wms.wms.dto.request.user.UserCreateRequest;
import com.wms.wms.dto.response.User.UserInfoResponse;
import com.wms.wms.entity.User;

import java.util.List;

public interface UserService {
    void create (UserCreateRequest request);

    void update (UserCreateRequest request);

    List<UserInfoResponse> findAll();

    void deleteById(Long userId);

}
