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
        orderItemList.forEach(materialOrder::addItem);

        // Save to db
        MaterialOrder dbOrder = materialOrderDAO.save(materialOrder);

        return convertOrderToResponse(dbOrder);
    }

    @Override
    public MaterialOrderResponse findById(int orderId) {
        log.info("Get Material_order detail service id: {}", orderId);
        MaterialOrder dbOrder = getMaterialOrder(orderId);
        return convertOrderToResponse(dbOrder);
    }


    /**
     * Find Material order by id
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


    /**
     * Convert List<OrderItemRequestDTO> to List<OrderItem>
     *
     * @param orderItemRequestDTOList list of item request
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


    private List<OrderItemResponse> convertItemsToResponse(List<OrderItem> orderItemList) {
        if (orderItemList == null) {
            return null;
        }
        return orderItemList.stream().map(OrderItemResponseMapper.INSTANCE::itemToResponse).toList();
    }

    private MaterialOrderResponse convertOrderToResponse(MaterialOrder materialOrder) {
        if (materialOrder == null) {
            return null;
        }
        MaterialOrderResponse orderResponse = MaterialOrderResponseMapper.INSTANCE.orderToResponse(materialOrder);
        orderResponse.setOrderItems(convertItemsToResponse(materialOrder.getOrderItems()));
        return orderResponse;
    }
}
