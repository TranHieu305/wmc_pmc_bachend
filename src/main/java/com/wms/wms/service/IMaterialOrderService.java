package com.wms.wms.service;

import com.wms.wms.dto.request.MaterialOrderRequestDTO;
import com.wms.wms.dto.response.MaterialOrderResponse;
import com.wms.wms.entity.MaterialOrder;

import java.util.List;

public interface IMaterialOrderService {

    // Add new material order
    MaterialOrderResponse save(MaterialOrderRequestDTO order);

    // Update material order
    MaterialOrderResponse update(MaterialOrderRequestDTO orderRequestDTO, int orderId);

    // Find material order by Id
    MaterialOrderResponse findById(int orderId);

    // Find all material orders
    List<MaterialOrderResponse> findAll();

    // Delete material order by Id
    void deleteById(int id);

}
