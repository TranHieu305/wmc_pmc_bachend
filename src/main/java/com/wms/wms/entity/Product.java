package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.AbstractEntity;
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
@Table(name = "product")
public class Product extends AbstractEntity {

    @Column(name = "name")
    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 1, max = 255, message = "Product name must be between 1 and 255 characters")
    private String name;

    @Column(name = "description")
    @Size(max = 255, message = "Product description must be under 256 characters")
    private  String description;

    @Column(name = "code")
    @Size(max = 63, message = "Product code must be under 63 characters")
    private String code;

    @Column(name = "uom")
    @NotBlank(message = "Product uom cannot be blank")
    @Size(max = 63, message = "Product uom must be under 63 characters")
    private String uom;

    @Column(name = "custom_fields")
    private String customFields;

    @Column(name = "images")
    @Lob
    private byte[] images;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory productCategory;
}
