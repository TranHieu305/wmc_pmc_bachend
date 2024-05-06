package com.wms.wms.dto.response;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponse {
    private int id;
    private String name;
    private String description;
    private String code;
    private String uom;
    private String customFields;
    private byte[] images;
    private ProductCategoryDetailResponse productCategory;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
