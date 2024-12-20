package com.wms.wms.dto.response.order;

import com.wms.wms.dto.response.BaseLogisticItemResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemResponse extends BaseLogisticItemResponse {
    private BigDecimal packedQuantity;
    private Long orderId;
    private BigDecimal deliveredQuantity;
}
