package com.wms.wms.service;

import com.wms.wms.dto.request.MaterialOrderRequestDTO;
import com.wms.wms.dto.response.MaterialOrderResponse;

public interface IMaterialOrderService {

    // Add new material order
    MaterialOrderResponse save(MaterialOrderRequestDTO order);

    MaterialOrderResponse findById(int orderId);
}
