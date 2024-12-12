package com.wms.wms.dto.response.product;

import com.wms.wms.dto.response.BaseEntityResponse;
import com.wms.wms.entity.ProductCategory;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
public class ProductResponse extends BaseEntityResponse {
    private String name;
    private String description;
    private String code;
    private String uom;
    private ProductCategory productCategory;
    private BigDecimal quantity = BigDecimal.ZERO;
}
