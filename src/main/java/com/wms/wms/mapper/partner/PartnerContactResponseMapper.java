package com.wms.wms.mapper.partner;

import com.wms.wms.dto.response.partner.PartnerContactResponse;
import com.wms.wms.entity.PartnerContact;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PartnerContactResponseMapper extends BaseMapper<PartnerContactResponse, PartnerContact> {
    PartnerContactResponseMapper INSTANCE = Mappers.getMapper(PartnerContactResponseMapper.class);
}
