package com.wms.wms.dto.request;

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
}
