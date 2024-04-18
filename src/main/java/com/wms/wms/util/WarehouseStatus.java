package com.wms.wms.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum WarehouseStatus {
    @JsonProperty("active")
    ACTIVE,
    @JsonProperty("inactive")
    INACTIVE
}
