package com.wms.wms.dto.request.warehouse;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class WarehouseRequest implements Serializable {
    private Long id;

    @NotBlank(message = "Warehouse name cannot be blank")
    @Size(min = 1, max = 255, message = "Warehouse name must be between 1 and 255 characters")
    private String name;

    @NotBlank(message = "Warehouse description cannot be blank")
    @Size(min = 1, max = 255, message = "Warehouse description must be between 1 and 255 characters")
    private  String description;

    @NotBlank(message = "Warehouse address cannot be blank")
    @Size(min = 1, max = 255, message = "Warehouse address must be between 1 and 255 characters")
    private String address;

    @Digits(integer = 3, fraction = 16, message = "Warehouse longitude must be decimal")
    private BigDecimal longitude;

    @Digits(integer = 3, fraction = 16, message = "Warehouse latitude must be decimal")
    private BigDecimal latitude;

    private  Long supervisorId;

    private  boolean isActive;
}
