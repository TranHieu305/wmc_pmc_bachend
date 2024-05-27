package com.wms.wms.dto.response;

import com.wms.wms.entity.OrderItem;
import com.wms.wms.entity.Supplier;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialOrderDetailResponse {
    private int id;
    private int supplierId;
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
