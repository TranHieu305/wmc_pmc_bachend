package com.wms.wms.service.product;

import com.wms.wms.dto.request.product.ProductWarehouseHistoryRequest;
import com.wms.wms.dto.response.product.ProductWarehouseHistoryResponse;
import com.wms.wms.entity.Product;
import com.wms.wms.entity.ProductWarehouse;
import com.wms.wms.entity.ProductWarehouseHistory;
import com.wms.wms.entity.Warehouse;
import com.wms.wms.entity.enumentity.InventoryAction;
import com.wms.wms.entity.enumentity.ProcessType;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.product.ProductWarehouseHistoryRequestMapper;
import com.wms.wms.mapper.product.ProductWarehouseHistoryResponseMapper;
import com.wms.wms.repository.ProductRepository;
import com.wms.wms.repository.ProductWarehouseHistoryRepository;
import com.wms.wms.repository.ProductWarehouseRepository;
import com.wms.wms.repository.WarehouseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductWarehouseHistoryServiceImpl implements ProductWarehouseHistoryService{
    private final ProductWarehouseHistoryRepository productWarehouseHistoryRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductWarehouseRepository productWarehouseRepository;

    @Override
    public List<ProductWarehouseHistoryResponse> findAll() {
        return null;
    }

    @Override
    public ProductWarehouseHistoryResponse findById(Long entityId) {
        return null;
    }

    @Override
    public ProductWarehouseHistoryResponse save(ProductWarehouseHistoryRequest request) {
        // Validate product warehouse ID
        if (request.getId() != 0) {
            this.getPWAById(request.getId());
        }

        // Validate product ID
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("No Product exists with the given Id: " + request.getProductId()));

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("No Warehouse exists with the given Id: " + request.getWarehouseId()));

        // Map field
        ProductWarehouseHistory productWarehouseHistory = ProductWarehouseHistoryRequestMapper.INSTANCE.toEntity(request);
        productWarehouseHistory.setProduct(product);
        productWarehouseHistory.setWarehouse(warehouse);
        productWarehouseHistory.setProcessType(ProcessType.MANUAL);

        ProductWarehouseHistory dbPWH = productWarehouseHistoryRepository.save(productWarehouseHistory);
        log.info("Service PWH - Save Product warehouse history successfully");

        // Process pwa
        this.processProductWarehouseHistory(dbPWH);

        return ProductWarehouseHistoryResponseMapper.INSTANCE.toDto(dbPWH);
    }

    @Override
    public void processProductWarehouseHistory(ProductWarehouseHistory pwh) {
        log.info("Service PWH - Start process PWH");

        log.info("Service PWH - Find product warehouse");
        Optional<ProductWarehouse> optionalProductWarehouse =
                productWarehouseRepository.findByWarehouseIdAndProductId(pwh.getWarehouse().getId(), pwh.getProduct().getId());

        ProductWarehouse productWarehouse;
        if (optionalProductWarehouse.isPresent()) {
            productWarehouse = optionalProductWarehouse.get();
        }
        else {
            log.info("Service PWH - Not found product warehouse, creating new one");
            productWarehouse = ProductWarehouse.builder()
                    .warehouse(pwh.getWarehouse())
                    .product(pwh.getProduct())
                    .build();
        }

        // Process product warehouse history
        BigDecimal newQuantity;
        if (pwh.getInventoryAction() == InventoryAction.IMPORT) {
            newQuantity = productWarehouse.getQuantityOnHand().add(pwh.getQuantity());
        }
        else  {
            newQuantity = productWarehouse.getQuantityOnHand().subtract(pwh.getQuantity());
        }
        productWarehouse.setQuantityOnHand(newQuantity);

        log.info("Service PWH - Saving Product warehouse");
        productWarehouseRepository.save(productWarehouse);
        log.info("Service PWH - Save Product warehouse successfully");
    }

    @Override
    public void deleteById(Long id) {

    }

    private ProductWarehouseHistory getPWAById(Long id) {
        return productWarehouseHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Product warehouse history exists with the given Id: " + id));
    }
}
