package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.BaseEntity;
import com.wms.wms.entity.enumentity.type.InventoryAction;
import com.wms.wms.entity.enumentity.status.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "order_record")
public class Order extends BaseEntity {

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Order name cannot be blank")
    @Size(min = 1, max = 255, message = "Order name must be between 1 and 255 characters")
    private String name;

    @Column(name = "inventory_action", nullable = false)
    @NotNull(message = "Order inventory_action can not be null")
    @Enumerated(EnumType.STRING)
    private InventoryAction inventoryAction;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING_APPROVAL;

    @Column(name = "order_date")
    private Timestamp orderDate;

    @Column(name = "expected_delivery_date")
    private Timestamp expectedDeliveryDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "order_id")
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "approver_ids")
    private Set<Long> approverIds;

    @Column(name = "pending_approver_ids")
    private Set<Long> pendingApproverIds;

    @Column(name = "participants_id")
    private Set<Long> participantIds;

    // Utility method to add an order item
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    // Utility method to remove an order item
    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
    }
}
