package com.wms.wms.dto.response.order;

import com.wms.wms.dto.response.BaseEntityResponse;
import com.wms.wms.entity.Partner;
import com.wms.wms.entity.enumentity.type.InventoryAction;
import com.wms.wms.entity.enumentity.status.OrderStatus;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponse extends BaseEntityResponse {
    private Partner partner;
    private String name;
    private InventoryAction inventoryAction;
    private OrderStatus status;
    private Timestamp orderDate;
    private Timestamp expectedDeliveryDate;
    private List<OrderItemResponse> orderItems;
    private Set<Long> approverIds;
    private Set<Long> pendingApproverIds;
    private Set<Long> participantIds;
}
