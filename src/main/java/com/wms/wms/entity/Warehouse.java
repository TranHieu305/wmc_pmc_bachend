package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.BaseEntity;
import jakarta.persistence.*;
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
@Entity
@SuperBuilder
@Table(name="warehouse")
public class Warehouse extends BaseEntity {

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Warehouse name cannot be blank")
    @Size(min = 1, max = 255, message = "Warehouse name must be between 1 and 255 characters")
    private String name;

    @Column(name = "description")
    @Size(min = 1, max = 255, message = "Warehouse description must be between 1 and 255 characters")
    private  String description;

    @Column(name = "address")
    @Size(min = 1, max = 255, message = "Warehouse address must be between 1 and 255 characters")
    private String address;

    @Column(name = "longitude")
    @Digits(integer = 3, fraction = 16, message = "Warehouse longitude must be decimal")
    private BigDecimal longitude = BigDecimal.ZERO;

    @Column(name = "latitude")
    @Digits(integer = 3, fraction = 16, message = "Warehouse latitude must be decimal")
    private BigDecimal latitude = BigDecimal.ZERO;

    @Column(name = "supervisor_id")
    private  Long supervisorId;

    @Column(name = "is_active")
    @Builder.Default
    private  boolean isActive = true;
}
