package com.wms.wms.mapper.partner;

import com.wms.wms.dto.request.partner.PartnerAddressRequest;
import com.wms.wms.entity.PartnerAddress;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PartnerAddressRequestMapper extends BaseMapper<PartnerAddressRequest, PartnerAddress> {
    PartnerAddressRequestMapper INSTANCE = Mappers.getMapper(PartnerAddressRequestMapper.class);
}
