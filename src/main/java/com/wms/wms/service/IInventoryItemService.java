package com.wms.wms.service;

import com.wms.wms.entity.OrderItem;

import java.util.List;

public interface IInventoryItemService {
    void convertOrderItemsToInventoryItems(List<OrderItem> orderItems);
}
