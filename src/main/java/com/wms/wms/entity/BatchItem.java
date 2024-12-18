package com.wms.wms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.wms.wms.entity.baseentity.BaseLogisticItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Column(name = "weight")
    private BigDecimal weight;
}
