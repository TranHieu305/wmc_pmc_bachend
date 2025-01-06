package com.wms.wms.entity.enumentity.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    PENDING_APPROVAL,
    PENDING,
    COMPLETED,
    REJECTED
}
