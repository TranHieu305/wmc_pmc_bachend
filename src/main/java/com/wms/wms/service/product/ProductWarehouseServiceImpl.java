package com.wms.wms.service.product;

import com.wms.wms.dto.response.product.ProductWarehouseResponse;
import com.wms.wms.entity.*;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.product.ProductWarehouseResponseMapper;
import com.wms.wms.repository.ProductWarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductWarehouseServiceImpl implements ProductWarehouseService {
    private final ProductWarehouseRepository productWarehouseRepository;


    @Override
    @Transactional
    public ProductWarehouseResponse save(ProductWarehouse request) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public ProductWarehouseResponse findById(Long id) {
        ProductWarehouse productWarehouse = this.getProductWarehouseById(id);
        log.info("Service Get Product warehouse detail ID: {} successfully ", id);
        return ProductWarehouseResponseMapper.INSTANCE.toDto(productWarehouse);
    }

    @Override
    public List<ProductWarehouseResponse> findAll() {
        List<ProductWarehouse> productWarehouses = productWarehouseRepository.findAll();
        log.info("Service Get All Product warehouse successfully");
        return ProductWarehouseResponseMapper.INSTANCE.toDtoList(productWarehouses);
    }

    @Override
    public List<ProductWarehouseResponse> findByWarehouseId(Long warehouseId) {
        List<ProductWarehouse> productWarehouses = productWarehouseRepository.findByWarehouseId(warehouseId);
        log.info("Service Get Product warehouse by warehouseID: {} successfully", warehouseId);
        return ProductWarehouseResponseMapper.INSTANCE.toDtoList(productWarehouses);
    }

    @Override
    public List<ProductWarehouseResponse> findByProductId(Long productId) {
        List<ProductWarehouse> productWarehouses = productWarehouseRepository.findByProductId(productId);
        log.info("Service Get Product warehouse by productId: {} successfully", productId);
        return ProductWarehouseResponseMapper.INSTANCE.toDtoList(productWarehouses);
    }

    private ProductWarehouse getProductWarehouseById(Long id) {
        return productWarehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Product warehouse exists with the given Id: " + id));
    }


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

//    private void exportLotItemsFromWarehouse(Lot lot) {
//        // TODO
//    }
}
