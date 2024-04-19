package com.wms.wms.dto.request;

import com.wms.wms.entity.MaterialOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {
    @NotBlank(message = "Order item name cannot be blank")
    @Size(min = 1, max = 255, message = "Order item name must be between 1 and 255 characters")
    private String name;

    private String orderType;

    private String uom;

    @Digits(integer = 10, fraction = 6, message = "Order item quantity must be decimal")
    private BigDecimal quantity;

    @Digits(integer = 10, fraction = 6, message = "Order item price must be decimal")
    private BigDecimal price;

    private MaterialOrder materialOrder;
}
