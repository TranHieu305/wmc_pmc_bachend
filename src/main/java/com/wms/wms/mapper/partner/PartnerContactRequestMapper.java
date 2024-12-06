package com.wms.wms.mapper.partner;

import com.wms.wms.dto.request.partner.PartnerContactRequest;
import com.wms.wms.entity.PartnerContact;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PartnerContactRequestMapper extends BaseMapper<PartnerContactRequest, PartnerContact> {
    PartnerContactRequestMapper INSTANCE = Mappers.getMapper(PartnerContactRequestMapper.class);
}
