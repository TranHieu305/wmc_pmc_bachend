package com.wms.wms.dto.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wms.wms.entity.MaterialOrder;
import com.wms.wms.entity.enumentity.OrderItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private int id;
    private String name;
    private OrderItemType orderType;
    private String uom;
    private BigDecimal quantity;
    private BigDecimal price;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private int orderId;
}
