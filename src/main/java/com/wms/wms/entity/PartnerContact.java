package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name="partner_contact")
public class PartnerContact extends BaseEntity {
    @Column(name = "partner_id", nullable = false)
    private Long partnerId;

    @Column(name = "contact_name", nullable = false)
    @NotBlank(message = "Partner contact name cannot be blank")
    @Size(min = 1, max = 255, message = "Partner contact name must be between 1 and 255 characters")
    private String contactName;

    @Column(name = "phone_number", nullable = false)
    @Pattern(regexp="^(\\d[- .]*){7,15}$", message="Please provide a valid phone number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @Column(name = "email")
    @Email(message = "Please provide a valid email")
    private String email;

    @Column(name = "position")
    @Size(min = 1, max = 255, message = "Partner contact position must be between 1 and 255 characters")
    private String position;

    @Column(name = "is_primary")
    @Builder.Default
    private boolean isPrimary = false;
}
