package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.BaseLogisticItem;
import com.wms.wms.entity.enumentity.status.ProducedItemStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name="produced_item")
public class ProducedItem extends BaseLogisticItem {
    @Column(name = "batch_id", nullable = false)
    private Long batchId;

    @Column(name = "batch_item_id", nullable = false)
    private Long batchItemId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ProducedItemStatus status = ProducedItemStatus.PENDING_APPROVAL;

    @Column(name = "manufacturer_ids", nullable = false)
    @Builder.Default
    private Set<Long> manufacturerIds = new HashSet<>();

    @Column(name = "approver_ids")
    @Builder.Default
    private Set<Long> approverIds = new HashSet<>();

    @Column(name = "pending_approver_ids")
    @Builder.Default
    private Set<Long> pendingApproverIds = new HashSet<>();;
}
