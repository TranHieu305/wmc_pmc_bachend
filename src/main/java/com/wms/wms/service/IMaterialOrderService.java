package com.wms.wms.service;

import com.wms.wms.dto.request.MaterialOrderRequestDTO;
import com.wms.wms.dto.response.MaterialOrderDetailResponse;

import java.util.List;

public interface IMaterialOrderService {

    // Add new material order
    MaterialOrderDetailResponse save(MaterialOrderRequestDTO order);

    // Find material order by Id
    MaterialOrderDetailResponse findById(int orderId);

    // Find all material orders
    List<MaterialOrderDetailResponse> findAll();

    // Delete material order by Id
    void deleteById(int id);

}
