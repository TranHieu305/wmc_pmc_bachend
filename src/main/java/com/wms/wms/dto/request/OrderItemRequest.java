package com.wms.wms.dto.request;

import jakarta.validation.constraints.Digits;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderItemRequest {
    private int id;
    private int productId;

    @Digits(integer = 10, fraction = 6, message = "Order item quantity must be decimal")
    private BigDecimal quantity;
}
