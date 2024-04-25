package com.wms.wms.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryRequestDTO {
    private int id;

    @Size(min = 1, max = 255, message = "Product category name must be between 1 and 255 characters")
    private String name;

    @Size(min = 1, max = 255, message = "Product category description must be between 1 and 255 characters")
    private  String description;
}
