package com.wms.wms.mapper.partner;

import com.wms.wms.dto.request.partner.PartnerRequest;
import com.wms.wms.entity.Partner;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PartnerRequestMapper extends BaseMapper<PartnerRequest, Partner> {
    PartnerRequestMapper INSTANCE = Mappers.getMapper(PartnerRequestMapper.class);
}
