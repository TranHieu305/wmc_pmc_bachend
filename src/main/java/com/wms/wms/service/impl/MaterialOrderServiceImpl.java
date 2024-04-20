package com.wms.wms.service.impl;

import com.wms.wms.dao.IMaterialOrderDAO;
import com.wms.wms.dto.request.MaterialOrderRequestDTO;
import com.wms.wms.dto.request.OrderItemRequestDTO;
import com.wms.wms.dto.response.MaterialOrderResponse;
import com.wms.wms.entity.MaterialOrder;
import com.wms.wms.entity.OrderItem;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.materialorder.MaterialOrderRequestMapper;
import com.wms.wms.mapper.materialorder.MaterialOrderResponseMapper;
import com.wms.wms.mapper.orderitem.OrderItemRequestMapper;
import com.wms.wms.service.IMaterialOrderService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.hibernate.query.Order;
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
        log.info("Request add material order");
        // Convert RequestDTO to entity
        MaterialOrder materialOrder = MaterialOrderRequestMapper.INSTANCE.requestToOrder(requestDTO);
        List<OrderItem> orderItemList = convertToItems(requestDTO.getOrderItems());
        orderItemList.forEach(orderItem -> materialOrder.addItem(orderItem));

        // Save to db
        MaterialOrder dbOrder = materialOrderDAO.save(materialOrder);

        return MaterialOrderResponseMapper.INSTANCE.orderToResponse(dbOrder);
    }

    @Override
    public MaterialOrderResponse findById(int orderId) {
        log.info("Get Material_order detail service id: {}", orderId);
        MaterialOrder order = getMaterialOrder(orderId);
        return MaterialOrderResponseMapper.INSTANCE.orderToResponse(order);
    }


    /**
     * Find Material order by id
     *
     * @param orderId
     * @return MaterialOrder || ResourceNotFoundException
     */
    private MaterialOrder getMaterialOrder(int orderId) {
        MaterialOrder dbOrder = materialOrderDAO.findById(orderId);
        if (dbOrder == null) {
            throw new ResourceNotFoundException("Material order not found");
        }
        return  dbOrder;
    }


    /**
     * Convert List<OrderItemRequestDTO> to List<OrderItem>
     *
     * @param orderItemRequestDTOList
     * @return List<OrderItem> || null
     */
    private List<OrderItem> convertToItems(List<OrderItemRequestDTO> orderItemRequestDTOList) {
        if (orderItemRequestDTOList == null) {
            return null;
        }
        List<OrderItem> orderItemList = orderItemRequestDTOList.stream().map(requestDTO -> {
            OrderItem orderItem = OrderItemRequestMapper.INSTANCE.requestToOrderItem(requestDTO);
            orderItem.setOrderType(OrderItem.TYPE_MATERIAL);
            return orderItem;
        }).toList();
        return orderItemList;
    }
}
