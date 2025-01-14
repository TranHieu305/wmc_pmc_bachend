package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.BaseEntity;
import com.wms.wms.entity.enumentity.status.ShipmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "shipment_record")
public class Shipment extends BaseEntity {
    @Column(name = "name", nullable = false)
    @NotBlank(message = "Shipment name cannot be blank")
    @Size(min = 1, max = 255, message = "Shipment name must be between 1 and 255 characters")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(name = "date")
    private Timestamp date;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ShipmentStatus status = ShipmentStatus.PENDING_APPROVAL;

    @Column(name = "approver_ids")
    private Set<Long> approverIds;

    @Column(name = "pending_approver_ids")
    private Set<Long> pendingApproverIds;

    @Column(name = "participants_id")
    private Set<Long> participantIds;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "shipment_id")
    @Builder.Default
    private List<ShipmentBatch> shipmentBatches = new ArrayList<>();

    public void addItem(ShipmentBatch batch) {
        shipmentBatches.add(batch);
    }

}
