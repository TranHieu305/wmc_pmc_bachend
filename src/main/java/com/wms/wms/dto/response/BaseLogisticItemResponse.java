package com.wms.wms.dto.response;

import com.wms.wms.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseLogisticItemResponse extends BaseEntityResponse {
    private Product product;
    private String uom;
    private BigDecimal quantity;
}
