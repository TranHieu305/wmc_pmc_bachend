package com.wms.wms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@EntityListeners(AuditingEntityListener.class)

@Table(name = "product")
public class Product extends AbstractEntity{
    // Define fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;
}
