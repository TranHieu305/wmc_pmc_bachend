package com.wms.wms.service.product;

import com.wms.wms.dto.request.product.ProductWarehouseHistoryRequest;
import com.wms.wms.dto.response.product.ProductWarehouseHistoryResponse;
import com.wms.wms.entity.*;
import com.wms.wms.entity.enumentity.InventoryAction;
import com.wms.wms.entity.enumentity.ProcessType;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.product.ProductWarehouseHistoryRequestMapper;
import com.wms.wms.mapper.product.ProductWarehouseHistoryResponseMapper;
import com.wms.wms.repository.ProductRepository;
import com.wms.wms.repository.ProductWarehouseHistoryRepository;
import com.wms.wms.repository.ProductWarehouseRepository;
import com.wms.wms.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
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

    @Override
    @Transactional
    public void processImportBatchItems(Batch batch) {

        // Create product warehouse history from Imported batch items
        log.info("Service PWH - Start process import to pwh");
        List<ProductWarehouseHistory> historyList = batch.getBatchItems().stream()
                .map(batchItem -> (ProductWarehouseHistory) ProductWarehouseHistory.builder()
                        .product(batchItem.getProduct())
                        .warehouse(batch.getWarehouse())
                        .inventoryAction(InventoryAction.IMPORT)
                        .processType(ProcessType.AUTOMATIC)
                        .quantity(batchItem.getQuantity())
                        .description("Action automatically created by delivered batch ID:" + batch.getId())
                        .build())
                .toList();
        productWarehouseHistoryRepository.saveAll(historyList);
        log.info("Service PWH - Create product warehouse history successfully");

        // Update exist product warehouse quantity
        // If not exist, create new
        List<ProductWarehouse> productWarehouseList =  historyList.stream().map(historyItem -> {
            ProductWarehouse productWarehouse;

            Optional<ProductWarehouse> existProductWarehouse =
                    productWarehouseRepository.findByWarehouseIdAndProductId(
                            historyItem.getProduct().getId(),
                            historyItem.getWarehouse().getId()
                    );

            if (existProductWarehouse.isPresent()) {
                productWarehouse = existProductWarehouse.get();
                BigDecimal newQuantity =  productWarehouse.getQuantityOnHand().add(historyItem.getQuantity());
                productWarehouse.setQuantityOnHand(newQuantity);
            }
            else {
                productWarehouse = ProductWarehouse.builder()
                        .product(historyItem.getProduct())
                        .warehouse(historyItem.getWarehouse())
                        .quantityOnHand(historyItem.getQuantity())
                        .build();
            }

            return productWarehouse;
        }).toList();

        productWarehouseRepository.saveAll(productWarehouseList);
        log.info("Service PWH - Update product warehouse successfully");
    }

    @Override
    @Transactional
    public void processExportBatchItems(Batch batch) {

    }

    @Override
    public void processImportBatchItem(BatchItem item, Warehouse warehouse) {

    }

    @Override
    public void processExportBatchItem(BatchItem item, Warehouse warehouse) {

    }

    private ProductWarehouseHistory getPWAById(Long id) {
        return productWarehouseHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Product warehouse history exists with the given Id: " + id));
    }
}
