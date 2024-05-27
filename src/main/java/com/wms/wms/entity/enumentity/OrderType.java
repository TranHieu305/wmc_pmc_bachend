package com.wms.wms.entity.enumentity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderType {
    MATERIAL_ORDER("MATERIAL_ORDER"),
    PRODUCTION_ORDER("PRODUCTION_ORDER");

    private final String name;
}
