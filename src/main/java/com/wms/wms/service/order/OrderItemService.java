package com.wms.wms.service.order;

import com.wms.wms.dto.request.order.OrderItemUpdateRequest;

public interface OrderItemService {
    void updateItem (OrderItemUpdateRequest request);

    void deleteItem (Long itemId);
    
}
