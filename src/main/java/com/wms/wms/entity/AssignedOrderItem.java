package com.wms.wms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.wms.wms.entity.enumentity.AssignedOrderItemStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="assigned_order_item")
public class AssignedOrderItem extends OrderItem {
    @Column(name = "order_item_id", nullable = false)
    private int orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id")
    @JsonBackReference
    private Lot lot;

    @Column(name = "assigned_to_id")
    private int assignedTo;

    @Column(name = "assigned_quantity")
    @Digits(integer = 10, fraction = 6, message = "Order item assigned quantity must be decimal")
    private BigDecimal assignedQuantity;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AssignedOrderItemStatus status = AssignedOrderItemStatus.PENDING;
}
