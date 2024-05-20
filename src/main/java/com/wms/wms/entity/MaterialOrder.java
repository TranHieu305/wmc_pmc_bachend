package com.wms.wms.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.query.Order;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="material_order")
public class MaterialOrder extends  AbstractEntity{
    // Define constants
    public static String STATUS_DRAFT = "draft";
    public static String STATUS_PENDING = "pending";
    public static String STATUS_APPROVED = "approved";

    // Define fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Material order name cannot be blank")
    @Size(min = 1, max = 255, message = "Material order name must be between 1 and 255 characters")
    private String name;

    @Column(name = "order_date")
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Column(name = "expected_date")
    @Temporal(TemporalType.DATE)
    private Date expectedDate;

    @Column(name = "actual_date")
    @Temporal(TemporalType.DATE)
    private Date actualDate;

    @Column(name = "additional_data")
    private String additionalData;

    @Column(name = "status")
    private String status = STATUS_DRAFT;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "materialOrder")
    @JsonManagedReference
    private List<OrderItem> orderItems;

    public void addItem(OrderItem newOrderItem) {
        if (newOrderItem == null) {
            return;
        }
        if (orderItems  == null) {
            orderItems = new ArrayList<>();
        }
        orderItems.add(newOrderItem);
        newOrderItem.setMaterialOrder(this);
    }
}
