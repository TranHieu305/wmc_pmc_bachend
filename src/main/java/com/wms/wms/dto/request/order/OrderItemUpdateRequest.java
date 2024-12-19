package com.wms.wms.dto.request.order;

import jakarta.validation.constraints.Digits;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderItemUpdateRequest {
    private Long id;
    @Digits(integer = 10, fraction = 6, message = "OrderItemRequest quantity must be decimal")
    private BigDecimal quantity;
}
