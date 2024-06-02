package com.wms.wms.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
public class ProductWarehouseRequest {
    @Setter
    private int id;

    @Min(value = 1, message = "Product is invalid")
    private int productId;

    @Min(value = 1, message = "Warehouse is invalid")
    private int warehouseId;

    @Digits(integer = 10, fraction = 6, message = "Product quantity must be decimal")
    private BigDecimal quantityOnHand;
}
