package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.BaseEntity;
import com.wms.wms.entity.enumentity.status.BatchStatus;
import com.wms.wms.entity.enumentity.type.InventoryAction;
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
@Table(name = "batch_record")
public class Batch extends BaseEntity {
    @Column(name = "name", nullable = false)
    @NotBlank(message = "Batch name cannot be blank")
    @Size(min = 1, max = 255, message = "Batch name must be between 1 and 255 characters")
    private String name;

    @Column(name = "inventory_action", nullable = false)
    @NotNull(message = "Batch inventory_action can not be null")
    @Enumerated(EnumType.STRING)
    private InventoryAction inventoryAction;

    @Column(name = "order_inventory_action", nullable = false)
    @Enumerated(EnumType.STRING)
    private InventoryAction orderInventoryAction;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BatchStatus status = BatchStatus.PENDING_APPROVAL;

    @Column(name = "batch_date")
    private Timestamp batchDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "batch_id")
    @Builder.Default
    private List<BatchItem> batchItems = new ArrayList<>();

    @Column(name = "approver_ids")
    private Set<Long> approverIds;

    @Column(name = "pending_approver_ids")
    private Set<Long> pendingApproverIds;

    @Column(name = "participants_id")
    private Set<Long> participantIds;

    // Utility method to add a batch item
    public void addItem(BatchItem item) {
        batchItems.add(item);
    }

    // Utility method to remove a batch item
    public void removeItem(BatchItem item) {
        batchItems.remove(item);
    }
}
