package com.wms.wms.dto.request;

import com.wms.wms.entity.enumentity.OrderStatus;
import lombok.Getter;

@Getter
public class OrderStatusRequest {
    OrderStatus status;
}
