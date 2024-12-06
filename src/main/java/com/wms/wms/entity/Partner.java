package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.BaseEntity;
import com.wms.wms.entity.enumentity.PartnerType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "partner_id")
    private List<PartnerAddress> partnerAddresses;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "partner_id")
    private List<PartnerContact> partnerContacts;
}
