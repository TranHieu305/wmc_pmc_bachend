package com.wms.wms.entity.enumentity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    CREATED("CREATED"),
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    PROCESSING("PROCESSING"),
    DISPATCHED("DISPATCHED"),
    DELIVERED("DELIVERED"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED");

    private final String name;
}
