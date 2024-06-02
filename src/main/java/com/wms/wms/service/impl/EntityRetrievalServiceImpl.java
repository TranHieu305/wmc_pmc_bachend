package com.wms.wms.service.impl;

import com.wms.wms.entity.*;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.repository.*;
import com.wms.wms.service.EntityRetrievalService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EntityRetrievalServiceImpl implements EntityRetrievalService {
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductPriceRepository productPriceRepository;
    private final ProductWarehouseRepository productWarehouseRepository;
    private final LotRepository lotRepository;
    private final MaterialOrderRepository materialOrderRepository;
    private final WarehouseRepository warehouseRepository;
    private final OrderItemRepository orderItemRepository;
    private final AssignedOrderItemRepository assignedOrderItemRepository;

    @Override
    public Warehouse getWarehouseById(int warehouseId) {
        return warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("No warehouse exists with the given Id: " + warehouseId));
    }

    @Override
    public Supplier getSupplierById(int supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("No supplier exists with the given Id: " + supplierId));
    }

    @Override
    public Product getProductById(int productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("No Product exists with the given Id: " + productId));
    }

    @Override
    public ProductCategory getProductCategoryById(int categoryId) {
        return productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException ("No Product category exists with the given Id: " + categoryId));
    }

    @Override
    public AbstractPartner getPartnerById(int partnerId) {
        Optional<Supplier> supplier = supplierRepository.findById(partnerId);
        if (supplier.isPresent()) {
            return supplier.get();
        }
        throw new EntityNotFoundException("Partner not exists");
    }

    /*
      ------------Lot---------------
     */
    @Override
    public Lot getLotById(int lotId) {
        return lotRepository.findById(lotId)
                .orElseThrow(() -> new ResourceNotFoundException ("No Lot exists with the given Id: " + lotId));
    }

    /*
     ------------Order---------------
    */
    @Override
    public AbstractOrder getOrderById(int orderId) {
        Optional<MaterialOrder> materialOrder = materialOrderRepository.findById(orderId);
        if (materialOrder.isPresent()) {
            return materialOrder.get();
        }
        throw new EntityNotFoundException("Order not exists");
    }

    /*
    ------------Order Item---------------
    */
    @Override
    public OrderItem getOrderItemById(int orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException ("No Order Item exists with the given Id: " + orderItemId));
    }

    @Override
    public List<OrderItem> getOrderItemByIds(Set<Integer> ids) {
        return orderItemRepository.findByIdIn(ids);
    }

    /*
        ------------Assigned Order Item---------------
        */
    @Override
    public List<AssignedOrderItem> getAssignedOrderItemByOrderItemId(int orderItemId) {
        return assignedOrderItemRepository.findByOrderItemId(orderItemId);
    }
    @Override
    public AssignedOrderItem getAssignedOrderItemById(int itemId) {
        return assignedOrderItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException ("No Assigned Order Item exists with the given Id: " + itemId));
    }

    /*
    ------------Product Warehouse---------------
    */

    @Override
    public ProductWarehouse getProductWarehouseById(int id) {
        return productWarehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException ("No Product Warehouse exists with the given Id: " + id));
    }

    @Override
    public List<ProductWarehouse> getAllProductOfWarehouse(int warehouseId) {
        return productWarehouseRepository.findByWarehouseId(warehouseId);
    }

    @Override
    public List<ProductWarehouse> findByWarehouseIdAndProductIdIn(int warehouseId, Set<Integer> productIds) {
        return productWarehouseRepository.findByWarehouseIdAndProductIdIn(warehouseId, productIds);
    }
}
