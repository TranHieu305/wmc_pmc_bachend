package com.wms.wms.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private int partnerId;
    private BigDecimal price;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateApply;
    @Size(max = 255, message = "Product price description must be under 256 characters")
    private String description;
}
