package com.wms.wms.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wms.wms.entity.enumentity.LotStatus;
import com.wms.wms.entity.enumentity.LotType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="lot")
public class Lot extends AbstractEntity {

    @Column(name = "order_id")
    private int orderId;

    @Column(name = "warehouse_id")
    private int warehouseId;

    @Column(name = "name")
    @Size(min = 1, max = 255, message = "Lot name must be between 1 and 255 characters")
    private String name;

    @Column(name = "description")
    @Size(max = 255, message = "Lot description must be under 256 characters")
    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private LotType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LotStatus status;

    @Column(name = "date")
    private Date date;

    @OneToMany(mappedBy = "lot",
                cascade = CascadeType.ALL,
                fetch = FetchType.EAGER,
                orphanRemoval = true)
    @JsonManagedReference
    private List<AssignedOrderItem> assignedOrderItems = new ArrayList<>();

    public void addAssignedOrderItem(AssignedOrderItem assignedOrderItem) {
        assignedOrderItems.add(assignedOrderItem);
        assignedOrderItem.setLot(this);
    }

    public void removeAssignedOrderItem(AssignedOrderItem assignedOrderItem) {
        assignedOrderItems.remove(assignedOrderItem);
        assignedOrderItem.setLot(null);
    }

    public boolean isMaterialLot() {
        return this.type.equals(LotType.MATERIAL);
    }

    public boolean isProductionLot() {
        return this.type.equals(LotType.PRODUCTION);
    }

    public boolean canUpdate() {
        return true;
    }
}
