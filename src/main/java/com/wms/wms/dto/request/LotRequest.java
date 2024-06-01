package com.wms.wms.dto.request;

import com.wms.wms.entity.enumentity.LotStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
public class LotRequest {
    @Setter
    private int id;

    @Min(value = 1, message = "Order is not valid")
    private int orderId;

    @Min(value = 1, message = "Warehouse is not valid")
    private int warehouseId;

    @Size(min = 1, max = 255, message = "Lot name must be between 1 and 255 characters")
    private String name;

    @Size(max = 255, message = "Lot description must be under 256 characters")
    private String description;

    @Enumerated(EnumType.STRING)
    private LotStatus status;

    @Column(name = "date")
    private Date date;

    @NotEmpty(message = "Assigned order item list cannot be empty")
    List<AssignedOrderItemRequest> assignedOrderItems;
}
