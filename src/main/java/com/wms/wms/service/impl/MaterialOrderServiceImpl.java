package com.wms.wms.service.impl;

import com.wms.wms.dao.IMaterialOrderDAO;
import com.wms.wms.dto.request.MaterialOrderRequestDTO;
import com.wms.wms.dto.request.OrderItemRequestDTO;
import com.wms.wms.dto.response.MaterialOrderResponse;
import com.wms.wms.entity.MaterialOrder;
import com.wms.wms.entity.OrderItem;
import com.wms.wms.mapper.materialorder.MaterialOrderRequestMapper;
import com.wms.wms.mapper.materialorder.MaterialOrderResponseMapper;
import com.wms.wms.service.IMaterialOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialOrderServiceImpl implements IMaterialOrderService {
    IMaterialOrderDAO materialOrderDAO;
    MaterialOrderRequestMapper materialOrderRequestMapper;
    MaterialOrderResponseMapper materialOrderResponseMapper;

    @Autowired
    public MaterialOrderServiceImpl(IMaterialOrderDAO materialOrderDAO,
                                    MaterialOrderRequestMapper materialOrderRequestMapper,
                                    MaterialOrderResponseMapper materialOrderResponseMapper) {
        this.materialOrderDAO = materialOrderDAO;
        this.materialOrderRequestMapper = materialOrderRequestMapper;
        this.materialOrderResponseMapper = materialOrderResponseMapper;
    }

    @Override
    public MaterialOrderResponse save(MaterialOrderRequestDTO requestDTO) {
        MaterialOrder materialOrder = materialOrderRequestMapper.requestToOrder(requestDTO);

        return null;
    }

    private List<OrderItem> convertToItems(List<OrderItemRequestDTO> orderItemRequestDTOList) {
        return null;
    }
}
