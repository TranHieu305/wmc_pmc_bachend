package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.BaseLogisticItem;
import com.wms.wms.entity.enumentity.type.InventoryAction;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "inventory_item")
public class InventoryItem extends BaseLogisticItem {

    @Column(name = "batch_id", nullable = false)
    private Long batchId;

    @Column(name = "batch_name", nullable = false)
    private String batchName;

    @Column(name = "warehouse_id", nullable = false)
    private Long warehouseId;

    @Column(name = "warehouse_name", nullable = false)
    private String warehouseName;

    @Column(name = "inventory_action", nullable = false)
    @Enumerated(EnumType.STRING)
    private InventoryAction inventoryAction;
}
