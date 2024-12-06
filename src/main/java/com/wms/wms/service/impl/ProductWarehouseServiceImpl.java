//package com.wms.wms.service.impl;
//
//
//import com.wms.wms.dto.request.ProductWarehouseRequest;
//import com.wms.wms.dto.response.product.ProductWarehouseResponse;
//import com.wms.wms.entity.*;
//import com.wms.wms.repository.ProductWarehouseRepository;
//import com.wms.wms.service.ProductWarehouseService;
//import jakarta.transaction.Transactional;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//@AllArgsConstructor(onConstructor = @__(@Autowired))
//public class ProductWarehouseServiceImpl implements ProductWarehouseService {
//    private final ProductWarehouseRepository productWarehouseRepository;
//    private final EntityRetrievalService entityRetrievalService;
//
//    @Override
//    @Transactional
//    public ProductWarehouseResponse save(ProductWarehouseRequest request) {
//        ProductWarehouse productWarehouse;
//        if (request.getId() != 0) {
//            productWarehouse = entityRetrievalService.getProductWarehouseById(request.getId());
//        }
//        else {
//            productWarehouse = ProductWarehouse.builder().build();
//        }
//
//        // Validate
//        entityRetrievalService.getProductById(request.getProductId());
//        entityRetrievalService.getWarehouseById(request.getWarehouseId());
//
//        // Map field
//        productWarehouse.setWarehouseId(request.getWarehouseId());
//        productWarehouse.setProductId(request.getProductId());
//        productWarehouse.setQuantityOnHand(request.getQuantityOnHand());
//
//        ProductWarehouse dbProductWarehouse = productWarehouseRepository.save(productWarehouse);
//        log.info("Save Product warehouse successfully");
//
//        return this.convertToResponse(dbProductWarehouse);
//    }
//
//    @Override
//    public ProductWarehouseResponse findById(int id) {
//        ProductWarehouse productWarehouse = entityRetrievalService.getProductWarehouseById(id);
//        log.info("Get Product warehouse detail ID: {} successfully ", id);
//        return this.convertToResponse(productWarehouse);
//    }
//
//    @Override
//    public List<ProductWarehouseResponse> findAll() {
//        List<ProductWarehouse> productWarehouses = productWarehouseRepository.findAll();
//        List<ProductWarehouseResponse> responses = productWarehouses.stream()
//                .map(this::convertToResponse)
//                .toList();
//        log.info("Get All Product warehouse successfully");
//        return responses;
//    }
//
//    private ProductWarehouseResponse convertToResponse(ProductWarehouse productWarehouse) {
//        return ProductWarehouseResponse.builder()
//                .id(productWarehouse.getId())
//                .productId(productWarehouse.getWarehouseId())
//                .warehouseId(productWarehouse.getWarehouseId())
//                .quantityOnHand(productWarehouse.getQuantityOnHand())
//                .createdAt(productWarehouse.getCreatedAt())
//                .modifiedAt(productWarehouse.getModifiedAt())
//                .build();
//    }
//
//
//    @Override
//    @Transactional
//    public void importLotItemsToWarehouse(Lot lot) {
//        Warehouse warehouse = entityRetrievalService.getWarehouseById(lot.getWarehouseId());
//        List<AssignedOrderItem> assignedOrderItemList = lot.getAssignedOrderItems();
//
//        List<AssignedOrderItem> deliveredItems = assignedOrderItemList.stream()
//                .filter(AssignedOrderItem::isDelivered)
//                .toList();
//
//        Set<Integer> productIds = deliveredItems.stream()
//                .map(AssignedOrderItem::getProductId)
//                .collect(Collectors.toSet());
//
//        List<ProductWarehouse> products = entityRetrievalService.findByWarehouseIdAndProductIdIn(lot.getWarehouseId(), productIds);
//
//        for (AssignedOrderItem item : deliveredItems) {
//            // Find existing product with the same product ID
//            ProductWarehouse productWarehouse = products.stream()
//                    .filter(p -> p.getProductId() == item.getProductId())
//                    .findFirst()
//                    .orElse(null);
//
//            // If not found, create new
//            if (productWarehouse == null) {
//                productWarehouse = ProductWarehouse.builder()
//                        .warehouseId(warehouse.getId())
//                        .productId(item.getProductId())
//                        .build();
//            }
//            // Add quantity from item with status DELIVERED to product warehouse
//            BigDecimal sumQuantity = productWarehouse.getQuantityOnHand().add(item.getAssignedQuantity());
//            productWarehouse.setQuantityOnHand(sumQuantity);
//
//            products.add(productWarehouse);
//        }
//
//        productWarehouseRepository.saveAll(products);
//        log.info("Import Items from Material Lot to Warehouse successfully");
//    }
//
//    private void exportLotItemsFromWarehouse(Lot lot) {
//        // TODO
//    }
//}
