package com.wms.wms.dto.request.partner;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PartnerAddressRequest {
    Long id;

    private Long partnerId;

    @NotBlank(message = "Partner address name cannot be blank")
    private String name;

    @Size(max = 255, message = "Partner address must be between 1 and 255 characters")
    private String address;

    @Digits(integer = 3, fraction = 16, message = "Partner address longitude must be decimal")
    private BigDecimal longitude;

    @Digits(integer = 3, fraction = 16, message = "Partner address latitude must be decimal")
    private BigDecimal latitude;
}
