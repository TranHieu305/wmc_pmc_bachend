package com.wms.wms.dto.request;

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
    private int id;
    private int productId;

    @Digits(integer = 10, fraction = 6, message = "Order item quantity must be decimal")
    private BigDecimal quantity;
}
