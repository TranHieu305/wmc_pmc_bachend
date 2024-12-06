package com.wms.wms.dto.request.partner;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PartnerContactRequest {
    private Long id;

    private Long partnerId;

    @NotBlank(message = "Partner contact name cannot be blank")
    @Size(min = 1, max = 255, message = "Partner contact name must be between 1 and 255 characters")
    private String contactName;

    @Pattern(regexp="^(\\d[- .]*){7,15}$", message="Please provide a valid phone number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @Email(message = "Please provide a valid email")
    private String email;

    @Size(min = 1, max = 255, message = "Partner contact position must be between 1 and 255 characters")
    private String position;

    @Column(name = "is_primary")
    private boolean isPrimary;
}
