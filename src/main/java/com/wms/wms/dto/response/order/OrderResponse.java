package com.wms.wms.dto.response.order;

import com.wms.wms.dto.response.BaseEntityResponse;
import com.wms.wms.entity.Partner;
import com.wms.wms.entity.enumentity.InventoryAction;
import com.wms.wms.entity.enumentity.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

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
}
