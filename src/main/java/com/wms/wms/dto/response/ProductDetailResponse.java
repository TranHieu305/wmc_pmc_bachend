package com.wms.wms.dto.response;

import com.wms.wms.entity.ProductCategory;
import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

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
    private int productCategoryId;
    private ProductCategory productCategory;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
