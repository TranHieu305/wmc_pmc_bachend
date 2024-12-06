package com.wms.wms.mapper.partner;

import com.wms.wms.dto.response.partner.PartnerAddressResponse;
import com.wms.wms.entity.PartnerAddress;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PartnerAddressResponseMapper extends BaseMapper<PartnerAddressResponse, PartnerAddress> {
    PartnerAddressResponseMapper INSTANCE = Mappers.getMapper(PartnerAddressResponseMapper.class);
}
