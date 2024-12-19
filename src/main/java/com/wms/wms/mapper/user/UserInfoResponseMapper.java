package com.wms.wms.mapper.user;

import com.wms.wms.dto.response.User.UserInfoResponse;
import com.wms.wms.entity.User;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface UserInfoResponseMapper extends BaseMapper<UserInfoResponse, User> {
    UserInfoResponseMapper INSTANCE = Mappers.getMapper(UserInfoResponseMapper.class);
}
