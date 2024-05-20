package com.wms.wms.mapper.materialorder.impl;

import com.wms.wms.dto.response.MaterialOrderResponse;
import com.wms.wms.dto.response.OrderItemResponse;
import com.wms.wms.entity.MaterialOrder;
import com.wms.wms.entity.OrderItem;
import com.wms.wms.mapper.orderitem.OrderItemResponseMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MaterialOrderResponseMapperImpl {
    public MaterialOrderResponse orderToResponse(MaterialOrder materialOrder) {
        if (materialOrder == null) {
            return null;
        } else {
            MaterialOrderResponse.MaterialOrderResponseBuilder builder = MaterialOrderResponse.builder();
            builder.id(materialOrder.getId());
            builder.name(materialOrder.getName());
            builder.orderDate(materialOrder.getOrderDate());
            builder.expectedDate(materialOrder.getExpectedDate());
            builder.actualDate(materialOrder.getActualDate());
            builder.additionalData(materialOrder.getAdditionalData());
            builder.status(materialOrder.getStatus());
            builder.createdAt(materialOrder.getCreatedAt());
            builder.modifiedAt(materialOrder.getModifiedAt());

            // Map Order Items
            List<OrderItemResponse> orderItems = this.toOrderItemResponse(materialOrder.getOrderItems());
            builder.orderItems(orderItems);
            return builder.build();
        }
    };

    private List<OrderItemResponse> toOrderItemResponse(List<OrderItem> orderItemList){
        if (orderItemList == null) {
            return null;
        }
        return orderItemList.stream()
                .map(OrderItemResponseMapper.INSTANCE::itemToResponse)
                .toList();
    };
}
