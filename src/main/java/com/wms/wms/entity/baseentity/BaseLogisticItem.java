package com.wms.wms.entity.baseentity;

import com.wms.wms.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseLogisticItem extends BaseEntity{
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "uom", nullable = false)
    @NotBlank(message = "Base logistic item uom cannot be blank")
    private String uom;

    @Column(name = "quantity")
    @Builder.Default
    private BigDecimal quantity = BigDecimal.ZERO;
}
