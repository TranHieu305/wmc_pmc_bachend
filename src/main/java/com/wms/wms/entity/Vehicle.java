package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.BaseEntity;
import com.wms.wms.entity.enumentity.status.VehicleStatus;
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
@Table(name="vehicle")
public class Vehicle extends BaseEntity {
    @Column(name = "name", nullable = false)
    @NotBlank(message = "Vehicle entity name cannot be blank")
    @Size(min = 1, max = 255, message = "Vehicle entity name must be between 1 and 255 characters")
    private String name;

    @Column(name = "description")
    @Size(min = 1, max = 255, message = "Vehicle entity description must be between 1 and 255 characters")
    private  String description;

    @Column(name = "license_plate", nullable = false)
    @Size(min = 1, max = 255, message = "Vehicle entity license plate must be between 1 and 255 characters")
    private  String licensePlate;

    @Column(name = "load_capacity", nullable = false)
    @Digits(integer = 10, fraction = 6, message = "Vehicle load capacity must be decimal")
    private BigDecimal loadCapacity;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private VehicleStatus status = VehicleStatus.AVAILABLE;
}
