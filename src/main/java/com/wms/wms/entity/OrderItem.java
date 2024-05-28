package com.wms.wms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.wms.wms.entity.enumentity.OrderItemType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="order_item")
public class OrderItem extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private AbstractOrder order;

    @Column(name = "order_type")
    @Enumerated(EnumType.STRING)
    private OrderItemType orderType;

    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;

    @Column(name = "product_name")
    @NotBlank(message = "Order item name cannot be blank")
    @Size(min = 1, max = 255, message = "Order item name must be between 1 and 255 characters")
    private String productName;

    @Column(name = "product_uom")
    @NotBlank(message = "Order item code cannot be blank")
    @Size(max = 63, message = "Order item code must be under 63 characters")
    private String productUom;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    @Column(name = "quantity")
    @Digits(integer = 10, fraction = 6, message = "Order item quantity must be decimal")
    private BigDecimal quantity;
}
