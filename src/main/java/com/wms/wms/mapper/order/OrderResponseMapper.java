package com.wms.wms.mapper.order;

import com.wms.wms.dto.response.order.OrderResponse;
import com.wms.wms.entity.Order;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderResponseMapper extends BaseMapper<OrderResponse, Order> {
    OrderResponseMapper INSTANCE = Mappers.getMapper(OrderResponseMapper.class);
}
