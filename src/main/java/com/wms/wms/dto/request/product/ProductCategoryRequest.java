package com.wms.wms.dto.request.product;

import com.wms.wms.entity.enumentity.type.CategoryType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ProductCategoryRequest {
    private Long id;

    @NotBlank(message = "Product category name cannot be blank")
    @Size(min = 1, max = 255, message = "Product category name must be between 1 and 255 characters")
    private String name;

    @Size(max = 255, message = "Product category description must be under 256 characters")
    private  String description;

    @NotNull(message = "Product type can not be null")
    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;
}
