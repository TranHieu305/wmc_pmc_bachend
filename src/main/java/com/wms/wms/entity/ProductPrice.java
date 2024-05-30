package com.wms.wms.entity;

import com.wms.wms.entity.enumentity.ProductType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="product_price")
public class ProductPrice extends AbstractEntity {
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_type")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(name = "partner_id")
    private int partnerId;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "description")
    @Size(max = 255, message = "Product price description must be under 256 characters")
    private String description;
}
