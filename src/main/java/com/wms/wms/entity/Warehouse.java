package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.math.BigDecimal;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="warehouse")
public class Warehouse extends AbstractEntity {
    // Define constants
    public static String STATUS_ACTIVE = "active";
    public static String STATUS_INACTIVE = "inactive";

    @Column(name = "name")
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
    @Digits(integer = 10, fraction = 6, message = "Warehouse longitude must be decimal")
    private BigDecimal longitude = BigDecimal.ZERO;

    @Column(name = "latitude")
    @Digits(integer = 10, fraction = 6, message = "Warehouse latitude must be decimal")
    private BigDecimal latitude = BigDecimal.ZERO;

    @Column(name = "supervisor")
    private  String supervisor;

    @Column(name = "status")
    private  String status = STATUS_ACTIVE;
}
