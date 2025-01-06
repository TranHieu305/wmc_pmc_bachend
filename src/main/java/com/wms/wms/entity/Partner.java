package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.BaseEntity;
import com.wms.wms.entity.enumentity.type.PartnerType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name="partner")
public class Partner extends BaseEntity {
    @Column(name = "name", nullable = false)
    @NotBlank(message = "Partner name cannot be blank")
    @Size(min = 1, max = 255, message = "Partner name must be between 1 and 255 characters")
    private String name;

    @Column(name = "type", nullable = false)
    @NotNull(message = "Partner type entity cannot be null")
    @Enumerated(EnumType.STRING)
    private PartnerType type;

    @Column(name = "description")
    @Size(max = 255, message = "Partner description must be between 1 and 255 characters")
    private  String description;

    @Column(name = "email", nullable = false)
    @Email(message = "Please provide a valid email")
    @NotNull(message = "Partner Email is required")
    private String email;

    @Column(name = "phone_number", nullable = false)
    @Pattern(regexp="^(\\d[- .]*){7,15}$", message="Please provide a valid phone number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "partner_id")
    @Builder.Default
    private List<PartnerAddress> partnerAddresses = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "partner_id")
    @Builder.Default
    private List<PartnerContact> partnerContacts = new ArrayList<>();
}
