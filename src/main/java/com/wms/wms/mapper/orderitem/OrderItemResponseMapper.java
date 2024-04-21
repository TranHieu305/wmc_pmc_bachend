package com.wms.wms.mapper.orderitem;

import com.wms.wms.dto.response.OrderItemResponse;
import com.wms.wms.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderItemResponseMapper {
    OrderItemResponseMapper INSTANCE = Mappers.getMapper(OrderItemResponseMapper.class);

    @Mapping(target = "orderId", source = "materialOrder.id")
    OrderItemResponse itemToResponse(OrderItem orderItem);
}
