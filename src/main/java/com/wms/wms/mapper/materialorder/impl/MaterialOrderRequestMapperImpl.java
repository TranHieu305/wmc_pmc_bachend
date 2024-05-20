package com.wms.wms.mapper.materialorder.impl;

import com.wms.wms.dto.request.MaterialOrderRequestDTO;
import com.wms.wms.dto.request.OrderItemRequestDTO;
import com.wms.wms.entity.MaterialOrder;
import com.wms.wms.entity.OrderItem;
import com.wms.wms.mapper.orderitem.OrderItemRequestMapper;
import com.wms.wms.util.StringHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MaterialOrderRequestMapperImpl {
    public MaterialOrder requestToOrder(MaterialOrderRequestDTO materialOrderRequestDTO) {
        if (materialOrderRequestDTO == null) {
            return null;
        } else {
            MaterialOrder.MaterialOrderBuilder materialOrderBuilder = MaterialOrder.builder();
            materialOrderBuilder.name(StringHelper.preProcess(materialOrderRequestDTO.getName()));
            materialOrderBuilder.orderDate(materialOrderRequestDTO.getOrderDate());
            materialOrderBuilder.expectedDate(materialOrderRequestDTO.getExpectedDate());
            materialOrderBuilder.actualDate(materialOrderRequestDTO.getActualDate());
            materialOrderBuilder.additionalData(materialOrderRequestDTO.getAdditionalData());
            materialOrderBuilder.status(materialOrderRequestDTO.getStatus());

            // Map Order Items
            List<OrderItem> orderItems = this.toOrderItemList(materialOrderRequestDTO.getOrderItems());
            materialOrderBuilder.orderItems(orderItems);

            MaterialOrder materialOrder = materialOrderBuilder.build();
            // Link Order Items to Material order
            orderItems.forEach(materialOrder::addItem);

            return materialOrder;
        }
    };

    private List<OrderItem> toOrderItemList (List<OrderItemRequestDTO> orderItemRequestDTOList) {
        if (orderItemRequestDTOList == null) {
            return null;
        }
        List<OrderItem> orderItemList = orderItemRequestDTOList.stream().map(requestDTO -> {
            OrderItem orderItem = OrderItemRequestMapper.INSTANCE.requestToOrderItem(requestDTO);
            orderItem.setOrderType(OrderItem.TYPE_MATERIAL);
            return orderItem;
        }).toList();
        return orderItemList;
    };
}
