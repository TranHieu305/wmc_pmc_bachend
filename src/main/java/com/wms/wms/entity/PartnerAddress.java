package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name="partner_address")
public class PartnerAddress extends BaseEntity {
    @Column(name = "partner_id")
    private Long partnerId;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Partner address name cannot be blank")
    @Size(min = 1, max = 255, message = "Partner address name must be between 1 and 255 characters")
    private String name;

    @Column(name = "address")
    @Size(max = 255, message = "Partner address must be between 1 and 255 characters")
    private String address;

    @Column(name = "longitude")
    @Digits(integer = 3, fraction = 16, message = "Partner address longitude must be decimal")
    @Builder.Default
    private BigDecimal longitude = BigDecimal.ZERO;

    @Column(name = "latitude")
    @Digits(integer = 3, fraction = 16, message = "Partner address latitude must be decimal")
    @Builder.Default
    private BigDecimal latitude = BigDecimal.ZERO;
}
