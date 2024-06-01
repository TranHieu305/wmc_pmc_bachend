package com.wms.wms.dto.response.lot;

import com.wms.wms.entity.Lot;
import com.wms.wms.entity.enumentity.AssignedOrderItemStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Builder
public class AssignedOrderItemResponse {
    private int id;
    private int orderItemId;
    private int productId;
    private int assignedTo;
    private BigDecimal assignedQuantity;
    private AssignedOrderItemStatus status;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
