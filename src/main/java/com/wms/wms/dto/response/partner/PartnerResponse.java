package com.wms.wms.dto.response.partner;

import com.wms.wms.dto.response.BaseEntityResponse;
import com.wms.wms.entity.enumentity.type.PartnerType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PartnerResponse extends BaseEntityResponse {
    private String name;

    private PartnerType type;

    private  String description;

    private String email;

    private String phoneNumber;

    private String address;

    private List<PartnerAddressResponse> partnerAddresses;

    private List<PartnerContactResponse> partnerContacts;
}
