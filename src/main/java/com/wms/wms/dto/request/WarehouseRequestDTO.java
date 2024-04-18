package com.wms.wms.dto.request;

import com.wms.wms.util.EnumPattern;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseRequestDTO implements Serializable {
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
    private BigDecimal longitude;

    @Column(name = "latitude")
    @Digits(integer = 10, fraction = 6, message = "Warehouse latitude must be decimal")
    private BigDecimal latitude;

    @Column(name = "supervisor")
    private  String supervisor;

    @Column(name = "status")
    private  String status;
}
