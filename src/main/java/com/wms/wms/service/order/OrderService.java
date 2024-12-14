package com.wms.wms.service.order;

import com.wms.wms.dto.request.order.OrderRequest;
import com.wms.wms.dto.response.order.OrderResponse;
import com.wms.wms.service.BaseService;

public interface OrderService extends BaseService<OrderRequest, OrderResponse> {
    OrderResponse create (OrderRequest orderRequest);

    OrderResponse update (OrderRequest orderRequest);
}
