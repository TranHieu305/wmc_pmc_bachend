package com.wms.wms.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wms.wms.entity.OrderItem;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialOrderResponse {
    private int id;
    private String name;
    private Date orderDate;
    private Date expectedDate;
    private Date actualDate;
    private String additionalData;
    private String status;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    @Setter
    private List<OrderItemResponse> orderItems;
}
