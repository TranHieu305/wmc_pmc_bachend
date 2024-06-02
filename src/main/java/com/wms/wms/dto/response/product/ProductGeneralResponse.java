package com.wms.wms.dto.response.product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductGeneralResponse {
    private int id;
    private String name;
    private String uom;
    private String categoryName;
}
