package com.wms.wms.dto.request;

import com.wms.wms.entity.enumentity.AssignedOrderItemStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class AssignedOrderItemRequest {
    private int id;

    @Min(value = 1, message = "Order item is not valid")
    private int orderItemId;

    @Min(value = 1, message = "Person assigned is not valid")
    private int assignedTo;

    @Digits(integer = 10, fraction = 6, message = "Assigned order item quantity must be decimal")
    private BigDecimal assignedQuantity;

    @Enumerated(EnumType.STRING)
    private AssignedOrderItemStatus status;
}
