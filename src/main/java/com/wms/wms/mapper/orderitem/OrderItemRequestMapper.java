package com.wms.wms.mapper.orderitem;


import com.wms.wms.dto.request.OrderItemRequestDTO;
import com.wms.wms.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderItemRequestMapper {
    OrderItemRequestMapper INSTANCE = Mappers.getMapper(OrderItemRequestMapper.class);

    OrderItem requestToOrderItem(OrderItemRequestDTO orderItemRequestDTO);
}
