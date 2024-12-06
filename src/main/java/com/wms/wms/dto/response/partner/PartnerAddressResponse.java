package com.wms.wms.dto.response.partner;

import com.wms.wms.dto.response.BaseEntityResponse;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PartnerAddressResponse extends BaseEntityResponse {
    private Long partnerId;

    private String name;

    private String address;

    private BigDecimal longitude;

    private BigDecimal latitude;
}
