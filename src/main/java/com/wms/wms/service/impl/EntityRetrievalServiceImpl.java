package com.wms.wms.service.impl;

import com.wms.wms.entity.AbstractPartner;
import com.wms.wms.entity.Product;
import com.wms.wms.entity.ProductCategory;
import com.wms.wms.entity.Supplier;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.repository.ProductCategoryRepository;
import com.wms.wms.repository.ProductRepository;
import com.wms.wms.repository.SupplierRepository;
import com.wms.wms.service.IEntityRetrievalService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EntityRetrievalServiceImpl implements IEntityRetrievalService {
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

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
}
