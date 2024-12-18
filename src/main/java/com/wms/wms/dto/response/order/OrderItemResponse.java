package com.wms.wms.dto.response.order;

import com.wms.wms.dto.response.BaseLogisticItemResponse;
import com.wms.wms.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemResponse extends BaseLogisticItemResponse {
    private BigDecimal packedQuantity;
}
