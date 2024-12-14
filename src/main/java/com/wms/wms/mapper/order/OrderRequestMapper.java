package com.wms.wms.mapper.order;

import com.wms.wms.dto.request.order.OrderRequest;
import com.wms.wms.entity.Order;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderRequestMapper extends BaseMapper<OrderRequest, Order> {
    OrderRequestMapper INSTANCE = Mappers.getMapper(OrderRequestMapper.class);
}
