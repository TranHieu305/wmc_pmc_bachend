package com.wms.wms.entity;

import com.wms.wms.entity.enumentity.ProductType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product_category")
public class ProductCategory extends AbstractEntity {

    @Column(name = "name")
    @NotBlank(message = "Product category name cannot be blank")
    @Size(min = 1, max = 255, message = "Product category name must be between 1 and 255 characters")
    private String name;

    @Column(name = "description")
    @Size(max = 255, message = "Product category description must be under 256 characters")
    private String description;

    @Column(name = "product_type")
    @Enumerated(EnumType.STRING)
    private ProductType productType;
}
