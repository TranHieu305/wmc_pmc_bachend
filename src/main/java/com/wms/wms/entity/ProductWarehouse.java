package com.wms.wms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product_warehouse")
public class ProductWarehouse extends AbstractEntity{

    @Column(name = "product_id")
    private int productId;

    @Column(name = "warehouse_id")
    private int warehouseId;

    @Column(name = "quantity_on_hand")
    private BigDecimal quantityOnHand;
}
