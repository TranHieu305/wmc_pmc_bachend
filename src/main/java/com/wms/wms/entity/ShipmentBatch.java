package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.BaseEntity;
import com.wms.wms.entity.enumentity.status.ShipmentBatchStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "shipment_batch")
public class ShipmentBatch extends BaseEntity {
    @Column(name = "shipment_id")
    private Long shipmentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @Column(name = "partner_id", nullable = false)
    private Long partnerId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partner_address_id", nullable = false)
    private PartnerAddress partnerAddress;

    @Column(name = "shipment_order", nullable = false)
    private Long shipmentOrder;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ShipmentBatchStatus status = ShipmentBatchStatus.PACKED;
}
