package com.wms.wms.dto.request.batch;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wms.wms.entity.enumentity.InventoryAction;
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
public class BatchRequest {
    private Long id;

    @NotBlank(message = "Order name cannot be blank")
    @Size(min = 1, max = 255, message = "Order name must be between 1 and 255 characters")
    private String name;

    private Long warehouseId;
    private Long orderId;

    @NotNull(message = "Order inventory_action can not be null")
    private InventoryAction inventoryAction;

    private Timestamp batchDate;

    // Note: Map manually
    @JsonProperty("batchItems")
    private List<BatchItemRequest> batchItemRequests;
}
