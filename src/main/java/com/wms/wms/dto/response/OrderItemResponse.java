package com.wms.wms.dto.response;

import com.wms.wms.entity.MaterialOrder;
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
    private String orderType;
    private String uom;
    private BigDecimal quantity;
    private BigDecimal price;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private MaterialOrder materialOrder;
}
