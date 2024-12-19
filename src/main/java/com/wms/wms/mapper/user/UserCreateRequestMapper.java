package com.wms.wms.mapper.user;

import com.wms.wms.dto.request.user.UserCreateRequest;
import com.wms.wms.entity.User;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserCreateRequestMapper extends BaseMapper<UserCreateRequest, User> {
    UserCreateRequestMapper INSTANCE = Mappers.getMapper(UserCreateRequestMapper.class);
}
