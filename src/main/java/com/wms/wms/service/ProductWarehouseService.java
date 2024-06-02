package com.wms.wms.service;

import com.wms.wms.entity.Lot;

public interface ProductWarehouseService {
    void processCompletedLot(Lot lot);
}
