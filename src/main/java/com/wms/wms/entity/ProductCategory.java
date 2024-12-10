package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.BaseEntity;
import com.wms.wms.entity.enumentity.CategoryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "product_category")
public class ProductCategory extends BaseEntity {

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Product category name cannot be blank")
    @Size(min = 1, max = 255, message = "Product category name must be between 1 and 255 characters")
    private String name;

    @Column(name = "description")
    @Size(max = 255, message = "Product category description must be under 256 characters")
    private String description;

    @Column(name = "category_type")
    @NotNull(message = "Category type can not be null")
    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;
}
