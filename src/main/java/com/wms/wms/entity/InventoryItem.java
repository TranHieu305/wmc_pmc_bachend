package com.wms.wms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "inventory_item")
public class InventoryItem extends AbstractEntity {

    @Column(name = "assigned_order_item_id")
    private int assignedOrderItemId;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "warehouse_id")
    private int warehouseId;

    @Column(name = "quantity_on_hand")
    private BigDecimal quantityOnHand;

    @Column(name = "datetime_received")
    private Date datetimeReceived;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;
}
