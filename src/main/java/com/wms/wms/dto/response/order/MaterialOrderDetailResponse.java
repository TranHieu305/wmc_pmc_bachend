//package com.wms.wms.dto.response.order;
//
//import com.wms.wms.entity.OrderItem;
//import com.wms.wms.entity.enumentity.OrderStatus;
//import lombok.*;
//
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.util.Date;
//import java.util.List;
//
//@Getter
//@Builder
//public class MaterialOrderDetailResponse {
//    private int id;
//    private int supplierId;
//    private String name;
//    private String partnerName;
//    private String partnerEmail;
//    private String partnerPhone;
//    private String partnerAddress;
//
//    private Date orderDate;
//    private Date expectedDate;
//    private Date actualDate;
//    private String additionalData;
//    private OrderStatus status;
//    private BigDecimal totalOrderCost;
//    private BigDecimal tax;
//
//    private Timestamp createdAt;
//    private Timestamp modifiedAt;
//    private List<OrderItem> orderItems;
//
//}
