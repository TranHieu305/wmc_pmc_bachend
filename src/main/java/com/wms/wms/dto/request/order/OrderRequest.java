package com.wms.wms.dto.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wms.wms.entity.enumentity.type.InventoryAction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {
    private Long id;

    @NotBlank(message = "Order name cannot be blank")
    @Size(min = 1, max = 255, message = "Order name must be between 1 and 255 characters")
    private String name;

    private Long partnerId;

    @NotNull(message = "Order inventory_action can not be null")
    private InventoryAction inventoryAction;

    private Timestamp orderDate;
    private Timestamp expectedDeliveryDate;

    // Note: Map manually
    @JsonProperty("orderItems")
    private List<OrderItemRequest> orderItemRequests;

    private List<Long> approverIds;
    private List<Long> participantIds;
}
