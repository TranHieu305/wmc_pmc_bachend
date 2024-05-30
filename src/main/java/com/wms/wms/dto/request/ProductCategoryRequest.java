package com.wms.wms.dto.request;

import com.wms.wms.entity.enumentity.ProductType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryRequest {
    @Setter
    private int id;

    @Size(min = 1, max = 255, message = "Product category name must be between 1 and 255 characters")
    private String name;

    @Size(max = 255, message = "Product category description must be under 256 characters")
    private  String description;

    @NotNull(message = "Product type can not be null")
    private ProductType productType;
}
