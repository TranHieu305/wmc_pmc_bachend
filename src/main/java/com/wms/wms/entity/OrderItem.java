package com.wms.wms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.wms.wms.entity.baseentity.BaseLogisticItem;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name="order_item")
public class OrderItem extends BaseLogisticItem {
    @Column(name = "packed_quantity")
    @Builder.Default
    private BigDecimal packedQuantity = BigDecimal.ZERO;
}
