package com.wms.wms.mapper.user;

import com.wms.wms.dto.response.User.UserGeneral;
import com.wms.wms.entity.User;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface UserGeneralMapper extends BaseMapper<UserGeneral, User> {
    UserGeneralMapper INSTANCE = Mappers.getMapper(UserGeneralMapper.class);
}
