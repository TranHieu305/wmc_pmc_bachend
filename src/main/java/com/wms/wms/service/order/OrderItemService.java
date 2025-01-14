package com.wms.wms.service.order;

import com.wms.wms.dto.request.order.OrderItemUpdateRequest;
import com.wms.wms.entity.BatchItem;
import com.wms.wms.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    void updateItem (OrderItemUpdateRequest request);

    void deleteItem (Long itemId);

    List<OrderItem> findByProductId(Long productId);

    void addQuantityDelivered(List<OrderItem> orderItems, List<BatchItem> batchItems);

    void subtractQuantityDelivered(List<OrderItem> orderItems, List<BatchItem> batchItems);
}
