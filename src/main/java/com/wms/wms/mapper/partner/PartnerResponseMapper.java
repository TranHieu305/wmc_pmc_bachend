package com.wms.wms.mapper.partner;

import com.wms.wms.dto.response.partner.PartnerResponse;
import com.wms.wms.entity.Partner;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring"
)
public interface PartnerResponseMapper extends BaseMapper<PartnerResponse, Partner> {
    PartnerResponseMapper INSTANCE = Mappers.getMapper(PartnerResponseMapper.class);
}
