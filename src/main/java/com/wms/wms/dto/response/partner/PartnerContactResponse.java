package com.wms.wms.dto.response.partner;

import com.wms.wms.dto.response.BaseEntityResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PartnerContactResponse extends BaseEntityResponse {
    private Long partnerId;

    private String contactName;

    private String phoneNumber;

    private String email;

    private String position;

    private boolean isPrimary;
}
