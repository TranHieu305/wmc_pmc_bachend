package com.wms.wms.dto.request.partner;

import com.wms.wms.entity.enumentity.type.PartnerType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PartnerRequest {
    private Long id;

    @NotBlank(message = "Partner name cannot be blank")
    @Size(min = 1, max = 255, message = "Partner name must be between 1 and 255 characters")
    private String name;

    @NotNull(message = "Partner type entity cannot be null")
    @Enumerated(EnumType.STRING)
    private PartnerType type;

    @Size(max = 255, message = "Partner description must be between 1 and 255 characters")
    private  String description;

    @Email(message = "Please provide a valid email")
    @NotNull(message = "Partner Email is required")
    private String email;

    @Pattern(regexp="^(\\d[- .]*){7,15}$", message="Please provide a valid phone number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    // Partner address section
    // Note: Manual match with partner_address entity
//    @NotBlank(message = "Partner address name cannot be blank")
    @Size(max = 255, message = "Partner address name must be between 1 and 255 characters")
    private String addressName;

    @Size(max = 255, message = "Partner address must be between 1 and 255 characters")
    private String address;
}
