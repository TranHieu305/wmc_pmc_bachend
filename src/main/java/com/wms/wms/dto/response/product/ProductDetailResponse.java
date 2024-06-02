package com.wms.wms.dto.response.product;

import com.wms.wms.entity.ProductCategory;
import com.wms.wms.entity.ProductPrice;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
public class ProductDetailResponse {
    private int id;
    private String name;
    private String description;
    private String code;
    private String uom;
    private String customFields;
    private byte[] images;
    private int productCategoryId;
    private ProductCategory productCategory;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private ProductPrice currentPrice;
}
