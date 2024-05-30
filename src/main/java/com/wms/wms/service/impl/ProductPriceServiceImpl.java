package com.wms.wms.service.impl;

import com.wms.wms.dto.request.ProductPriceRequest;
import com.wms.wms.entity.Product;
import com.wms.wms.entity.ProductPrice;
import com.wms.wms.entity.Supplier;
import com.wms.wms.entity.enumentity.ProductType;
import com.wms.wms.exception.ConstraintViolationException;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.repository.ProductPriceRepository;
import com.wms.wms.service.IProductPriceService;
import com.wms.wms.service.IProductService;
import com.wms.wms.service.ISupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductPriceServiceImpl implements IProductPriceService {
    private final ProductPriceRepository productPriceRepository;
    private final IProductService productService;
    private final ISupplierService supplierService;

    @Autowired
    public ProductPriceServiceImpl(ProductPriceRepository productPriceRepository,
                                   @Lazy IProductService productService,
                                   @Lazy ISupplierService supplierService) {
        this.productPriceRepository = productPriceRepository;
        this.productService = productService;
        this.supplierService = supplierService;
    }

    @Override
    public ProductPrice save(ProductPriceRequest request) {
        ProductPrice productPrice;

        // Get price
        if (request.getId() != 0) {
            productPrice = this.getProductPriceById(request.getId());
        } else {
            productPrice = ProductPrice.builder().build();
        }

        // Validate
        Product product = productService.getProductById(request.getProductId());
        ProductType productType = product.getProductCategory().getProductType();
        if (productType.equals(ProductType.MATERIAL)) {
            Supplier supplier = supplierService.getSupplierById(request.getPartnerId());
            productPrice.setPartnerId(supplier.getId());
        }

        productPrice.setProductId(product.getId());
        productPrice.setProductType(productType);
        productPrice.setPrice(request.getPrice());
        productPrice.setStartDate(request.getStartDate());

        // If currentPrice of product exists, update endDate of that price
        ProductPrice currentPrice = this.getCurrentPrice(product);
        if (currentPrice != null) {
            if (currentPrice.getStartDate().after(request.getStartDate())) {
                throw new ConstraintViolationException("Start Date must be after" + currentPrice.getStartDate());
            }
            currentPrice.setEndDate(request.getStartDate());
            productPriceRepository.save(currentPrice);
        }
        productPriceRepository.save(productPrice);
        log.info("Save Product price successfully with ID: {}", productPrice.getId());

        return productPrice;
    }

    @Override
    public void deleteById(int id) {
        ProductPrice productPrice = this.getProductPriceById(id);
        productPriceRepository.delete(productPrice);
        log.info("Delete Product price successfully with ID: {}", id);
    }

    @Override
    public ProductPrice getProductPriceById(int id) {
        return productPriceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Product price exists"));
    }

    @Override
    public ProductPrice getCurrentPrice(Product product) {
        return productPriceRepository.findLatestPriceByProductId(product.getId());
    }
}
