package com.wms.wms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    public static String TYPE_MATERIAL = "material";
    public static String TYPE_PRODUCT = "product";

    @Column(name = "name")
    @NotBlank(message = "Order item name cannot be blank")
    @Size(min = 1, max = 255, message = "Order item name must be between 1 and 255 characters")
    private String name;

    @Column(name = "order_type")
    @NotBlank(message = "Order item type cannot be blank")
    private String orderType;

    @Column(name = "uom")
    private String uom;

    @Column(name = "quantity")
    @Digits(integer = 10, fraction = 6, message = "Order item quantity must be decimal")
    private BigDecimal quantity;

    @Column(name = "price")
    @Digits(integer = 10, fraction = 6, message = "Order item price must be decimal")
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private MaterialOrder materialOrder;
}
