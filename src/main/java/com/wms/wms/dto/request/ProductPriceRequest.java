package com.wms.wms.dto.request;

import com.wms.wms.entity.enumentity.ProductType;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
public class ProductPriceRequest {
    @Setter
    private int id;
    private int productId;
    private ProductType productType;
    private int partnerId;
    private BigDecimal price;
    private Date startDate;
    private Date endDate;
    @Size(max = 255, message = "Product price description must be under 256 characters")
    private String description;
}
