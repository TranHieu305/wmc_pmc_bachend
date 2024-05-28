package com.wms.wms.dto.request;

import com.wms.wms.entity.enumentity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusRequest {
    OrderStatus status;
}
