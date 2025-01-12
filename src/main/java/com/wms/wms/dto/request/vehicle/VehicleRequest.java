package com.wms.wms.dto.request.vehicle;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class VehicleRequest {
    private Long id;

    @NotBlank(message = "Vehicle dto name cannot be blank")
    @Size(min = 1, max = 255, message = "Vehicle dto name must be between 1 and 255 characters")
    private String name;

    @Size(min = 1, max = 255, message = "Vehicle dto description must be between 1 and 255 characters")
    private  String description;

    @NotBlank(message = "Vehicle dto license plate cannot be blank")
    @Size(min = 1, max = 255, message = "Vehicle dto license plate must be between 1 and 255 characters")
    private  String licensePlate;

    @Digits(integer = 10, fraction = 6, message = "Vehicle dto load capacity must be decimal")
    private BigDecimal loadCapacity;
}
