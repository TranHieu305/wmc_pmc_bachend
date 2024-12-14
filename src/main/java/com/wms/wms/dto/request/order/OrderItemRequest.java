package com.wms.wms.dto.request.order;

import com.wms.wms.entity.Product;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemRequest {
    private Long id;

    // Note: Validate and Map manually at service
    private Long productId;

    private Product product;

    @NotBlank(message = "OrderItemRequest uom cannot be blank")
    private String uom;

    @Digits(integer = 10, fraction = 6, message = "OrderItemRequest quantity must be decimal")
    private BigDecimal quantity;

    private Long orderId;
}
