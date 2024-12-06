//package com.wms.wms.entity;
//
//
//import com.wms.wms.entity.enumentity.OrderStatus;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//import lombok.*;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//@DiscriminatorValue("MATERIAL_ORDER")
//@Table(name="material_order")
//public class MaterialOrder extends AbstractOrder{
//    public static BigDecimal IMPORT_TAX = new BigDecimal("0.1");
//
//    @Column(name = "supplier_id")
//    private int supplierId;
//
//    @Column(name = "name")
//    @NotBlank(message = "Material order name cannot be blank")
//    @Size(min = 1, max = 255, message = "Material order name must be between 1 and 255 characters")
//    private String name;
//
//    @Column(name = "order_date")
//    @Temporal(TemporalType.DATE)
//    private Date orderDate;
//
//    @Column(name = "expected_date")
//    @Temporal(TemporalType.DATE)
//    private Date expectedDate;
//
//    @Column(name = "actual_date")
//    @Temporal(TemporalType.DATE)
//    private Date actualDate;
//
//    @Column(name = "additional_data")
//    private String additionalData;
//
//    @Column(name = "status")
//    @Enumerated(EnumType.STRING)
//    @Builder.Default
//    private OrderStatus status = OrderStatus.CREATED;
//}
