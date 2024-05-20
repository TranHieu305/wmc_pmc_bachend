package com.wms.wms.service.impl;

import com.wms.wms.dao.IMaterialOrderDAO;
import com.wms.wms.dto.request.MaterialOrderRequestDTO;
import com.wms.wms.dto.request.OrderItemRequestDTO;
import com.wms.wms.dto.response.MaterialOrderResponse;
import com.wms.wms.dto.response.OrderItemResponse;
import com.wms.wms.entity.MaterialOrder;
import com.wms.wms.entity.OrderItem;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.materialorder.MaterialOrderRequestMapper;
import com.wms.wms.mapper.materialorder.MaterialOrderResponseMapper;
import com.wms.wms.mapper.orderitem.OrderItemRequestMapper;
import com.wms.wms.mapper.orderitem.OrderItemResponseMapper;
import com.wms.wms.service.IMaterialOrderService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MaterialOrderServiceImpl implements IMaterialOrderService {
    private final  IMaterialOrderDAO materialOrderDAO;

    @Autowired
    public MaterialOrderServiceImpl(IMaterialOrderDAO materialOrderDAO) {
        this.materialOrderDAO = materialOrderDAO;
    }

    @Transactional
    @Override
    public MaterialOrderResponse save(MaterialOrderRequestDTO requestDTO) {
        MaterialOrder materialOrder = MaterialOrderRequestMapper.INSTANCE.requestToOrder(requestDTO);
        MaterialOrder dbOrder = materialOrderDAO.save(materialOrder);
        log.info("Add material order successfully");
        return MaterialOrderResponseMapper.INSTANCE.orderToResponse(dbOrder);
    }

    @Override
    @Transactional
    public MaterialOrderResponse update(MaterialOrderRequestDTO orderRequestDTO, int orderId) {
        MaterialOrder oldOrder = getMaterialOrder(orderId);
        MaterialOrder newOrder = MaterialOrderRequestMapper.INSTANCE.requestToOrder(orderRequestDTO);
        newOrder.setId(oldOrder.getId());

        MaterialOrder newDbOrder = materialOrderDAO.save(newOrder);
        log.info("Update material order Id: {} successfully", newDbOrder.getId());
        return MaterialOrderResponseMapper.INSTANCE.orderToResponse(newDbOrder);
    }

    @Override
    public MaterialOrderResponse findById(int orderId) {
        MaterialOrder dbOrder = getMaterialOrder(orderId);
        log.info("Get Material_order detail id: {} successfully", orderId);
        return MaterialOrderResponseMapper.INSTANCE.orderToResponse(dbOrder);
    }


    @Override
    public List<MaterialOrderResponse> findAll() {
        List<MaterialOrder> materialOrderList = materialOrderDAO.findAll();
        List<MaterialOrderResponse> orderResponseList = new ArrayList<>();
        for (MaterialOrder order : materialOrderList) {
            MaterialOrderResponse response = MaterialOrderResponseMapper.INSTANCE.orderToResponse(order);
            orderResponseList.add(response);
        }
        log.info("Get all material orders successfully");
        return orderResponseList;
    }

    @Override
    @Transactional
    public void deleteById(int orderId) {
        MaterialOrder materialOrder = getMaterialOrder(orderId);
        materialOrderDAO.delete(materialOrder);
        log.info("Delete material order id: {} successfully", orderId);
    }


    /**
     * Find Material order by id from Database
     *
     * @param orderId order ID
     * @return MaterialOrder || ResourceNotFoundException
     */
    private MaterialOrder getMaterialOrder(int orderId) {
        MaterialOrder dbOrder = materialOrderDAO.findById(orderId);
        if (dbOrder == null) {
            throw new ResourceNotFoundException("Material order not found");
        }
        return  dbOrder;
    }
}
