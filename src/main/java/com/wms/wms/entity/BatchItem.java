package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.BaseLogisticItem;
import com.wms.wms.entity.enumentity.status.ItemStatus;
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
@Table(name="batch_item")
public class BatchItem extends BaseLogisticItem {

    @Column(name = "order_item_id", nullable = false)
    private Long orderItemId;

    @Column(name = "batch_id")
    private Long batchId;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ItemStatus status = ItemStatus.PENDING;
}
