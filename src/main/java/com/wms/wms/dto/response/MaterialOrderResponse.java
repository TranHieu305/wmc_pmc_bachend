package com.wms.wms.dto.response;

import com.wms.wms.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private List<OrderItem> orderItems;
}
