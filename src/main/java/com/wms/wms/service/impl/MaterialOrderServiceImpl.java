package com.wms.wms.service.impl;

import com.wms.wms.dto.request.MaterialOrderRequest;
import com.wms.wms.dto.request.OrderItemRequest;
import com.wms.wms.dto.request.OrderStatusRequest;
import com.wms.wms.dto.response.order.MaterialOrderDetailResponse;
import com.wms.wms.entity.MaterialOrder;
import com.wms.wms.entity.OrderItem;
import com.wms.wms.entity.Product;
import com.wms.wms.entity.Supplier;
import com.wms.wms.entity.enumentity.OrderItemType;
import com.wms.wms.entity.enumentity.OrderStatus;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.repository.MaterialOrderRepository;
import com.wms.wms.service.MaterialOrderService;
import com.wms.wms.service.OrderItemService;
import com.wms.wms.service.ProductService;
import com.wms.wms.service.SupplierService;
import com.wms.wms.util.StringHelper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MaterialOrderServiceImpl implements MaterialOrderService {
    private final MaterialOrderRepository materialOrderRepository;
    private final SupplierService supplierService;
    private final ProductService productService;
    private final OrderItemService orderItemService;

    @Transactional
    @Override
    public MaterialOrderDetailResponse save(MaterialOrderRequest requestDTO) {
        MaterialOrder materialOrder;
        if (requestDTO.getId() != 0) {
            materialOrder = this.getMaterialOrder(requestDTO.getId());
        }
        else {
            materialOrder = MaterialOrder.builder().build();
        }
        // Validate Supplier
        Supplier supplier = supplierService.getSupplierById(requestDTO.getSupplierId());
        materialOrder.setSupplierId(supplier.getId());
        materialOrder.setName(StringHelper.preProcess(requestDTO.getName()));
        materialOrder.setOrderDate(requestDTO.getOrderDate());
        materialOrder.setExpectedDate(requestDTO.getExpectedDate());
        materialOrder.setActualDate(requestDTO.getActualDate());

        // Remove old OrderItems
        if (!materialOrder.getOrderItems().isEmpty()) {
            List<OrderItem> existingItems = new ArrayList<>(materialOrder.getOrderItems());
            existingItems.forEach(materialOrder::removeOrderItem);
        }

        // Save new OrderItems
        if (!requestDTO.getOrderItems().isEmpty()) {
            List<OrderItem> newItems = this.convertToOrderItems(requestDTO.getOrderItems());
            newItems.forEach(materialOrder::addOrderItem);
        }

        MaterialOrder dbOrder = materialOrderRepository.save(materialOrder);
        log.info("Add material order successfully");
        return this.convertToDetailResponse(dbOrder);
    }

    // Map OrderItem request to OrderItem entity
    private List<OrderItem> convertToOrderItems(List<OrderItemRequest> requestDTOList) {
        return  requestDTOList.stream().map(requestDTO -> {
            // Validate product
            Product product = productService.getProductById(requestDTO.getProductId());

            // Map OrderItem
            OrderItem orderItem;
            if (requestDTO.getId() != 0) {
                orderItem = orderItemService.getById(requestDTO.getId());
            }
            else {
                orderItem = OrderItem.builder().build();
            }
            orderItem.setOrderType(OrderItemType.MATERIAL);
            orderItem.setProduct(product);
            orderItem.setProductName(product.getName());
            orderItem.setProductUom(product.getUom());
            //TODO: Set price
            orderItem.setQuantity(requestDTO.getQuantity());
            return orderItem;
        }).toList();
    }

    @Override
    @Transactional
    public void updateOrderStatus(int orderId, OrderStatusRequest request) {
        MaterialOrder order = this.getMaterialOrder(orderId);
        OrderStatus newStatus = request.getStatus();
        order.setStatus(newStatus);
        materialOrderRepository.save(order);
    }

    @Override
    public MaterialOrderDetailResponse findById(int orderId) {
        MaterialOrder dbOrder = getMaterialOrder(orderId);
        log.info("Get Material_order detail id: {} successfully", orderId);
        return this.convertToDetailResponse(dbOrder);
    }


    @Override
    public List<MaterialOrderDetailResponse> findAll() {
        List<MaterialOrder> materialOrderList = materialOrderRepository.findAll();
        List<MaterialOrderDetailResponse> orderResponseList = new ArrayList<>();
        materialOrderList.forEach(order -> orderResponseList.add(this.convertToDetailResponse(order)));
        log.info("Get all material orders successfully");
        return orderResponseList;
    }

    @Override
    @Transactional
    public void deleteById(int orderId) {
        MaterialOrder materialOrder = this.getMaterialOrder(orderId);
        materialOrderRepository.delete(materialOrder);
        log.info("Delete material order id: {} successfully", orderId);
    }

    /**
     * Find Material order by id from Database
     *
     * @param orderId order ID
     * @return MaterialOrder || ResourceNotFoundException
     */
    private MaterialOrder getMaterialOrder(int orderId) {
        return  materialOrderRepository
                .findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Material order not found with ID:" + orderId));
    }


    private MaterialOrderDetailResponse convertToDetailResponse(MaterialOrder materialOrder) {
        return MaterialOrderDetailResponse.builder()
                .id(materialOrder.getId())
                .supplierId(materialOrder.getSupplierId())
                .name(materialOrder.getName())
                .orderDate(materialOrder.getOrderDate())
                .expectedDate(materialOrder.getExpectedDate())
                .actualDate(materialOrder.getActualDate())
                .additionalData(materialOrder.getAdditionalData())
                .status(materialOrder.getStatus())
                .createdAt(materialOrder.getCreatedAt())
                .modifiedAt(materialOrder.getModifiedAt())
                .orderItems(materialOrder.getOrderItems())
                .build();
    }
}
