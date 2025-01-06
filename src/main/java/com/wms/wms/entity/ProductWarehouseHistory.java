package com.wms.wms.entity;

import com.wms.wms.entity.baseentity.BaseEntity;
import com.wms.wms.entity.enumentity.type.InventoryAction;
import com.wms.wms.entity.enumentity.type.ProcessType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "product_warehouse_history")
public class ProductWarehouseHistory extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(name = "inventory_action", nullable = false)
    @NotNull(message = "InventoryAction cannot be null")
    @Enumerated(EnumType.STRING)
    private InventoryAction inventoryAction;

    @Column(name = "process_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcessType processType;

    @Column(name = "quantity")
    @Digits(integer = 10, fraction = 6, message = "Product warehouse history quantity must be decimal")
    private BigDecimal quantity;

    @Column(name = "description")
    @Size(min = 1, max = 255, message = "Warehouse warehouse history description must be between 1 and 255 characters")
    private  String description;
}
