package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
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

    @Column(name = "partner_id")
    private int partnerId;

    @Column(name = "price")
    @Digits(integer = 10, fraction = 6, message = "Product price must be decimal")
    private BigDecimal price;

    @Column(name = "date_apply")
    private Date dateApply;

    @Column(name = "description")
    @Size(max = 255, message = "Product price description must be under 256 characters")
    private String description;
}
