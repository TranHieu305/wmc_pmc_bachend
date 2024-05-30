package com.wms.wms.dto.response.product;

import com.wms.wms.entity.enumentity.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDetailResponse {
    private int id;
    private String name;
    private String description;
    private ProductType productType;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
