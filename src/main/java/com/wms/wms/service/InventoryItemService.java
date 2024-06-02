package com.wms.wms.service;

import com.wms.wms.entity.Lot;
import com.wms.wms.entity.OrderItem;

import java.util.List;

public interface InventoryItemService {
    void processCompletedLot(Lot lot);
}
