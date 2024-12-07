package com.wms.wms.dto.response.warehouse;


import com.wms.wms.dto.response.BaseEntityResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class WarehouseResponse extends BaseEntityResponse {
    private String name;
    private  String description;
    private String address;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private  Long supervisorId;
    private  boolean isActive;
}
