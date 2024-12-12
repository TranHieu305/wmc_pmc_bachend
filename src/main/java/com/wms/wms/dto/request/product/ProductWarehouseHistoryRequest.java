package com.wms.wms.dto.request.product;

import com.wms.wms.entity.enumentity.InventoryAction;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductWarehouseHistoryRequest {
    @Setter
    private Long id;

    @Min(value = 1, message = "Product is invalid")
    private Long productId;

    @Min(value = 1, message = "Warehouse is invalid")
    private Long warehouseId;

    @NotNull(message = "InventoryAction cannot be null")
    private InventoryAction inventoryAction;

    @Digits(integer = 10, fraction = 6, message = "Product quantity must be decimal")
    private BigDecimal quantity;

    @NotBlank(message = "Product warehouse history description cannot be blank")
    @Size(min = 1, max = 255, message = "Product warehouse history description must be between 1 and 255 characters")
    private  String description;
}
