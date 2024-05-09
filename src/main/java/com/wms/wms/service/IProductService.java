package com.wms.wms.service;

import com.wms.wms.dto.request.ProductRequestDTO;
import com.wms.wms.dto.response.ProductDetailResponse;

import java.util.List;

public interface IProductService {
    // Create/ Update Product order
    ProductDetailResponse save(ProductRequestDTO requestDTO);

    // Find Product by ID
    ProductDetailResponse findById(int productId);

    // Find Product by  name
    List<ProductDetailResponse> findByName(String name);

    // Find all Product
    List<ProductDetailResponse> findAll();

    // Delete Product by ID
    void deleteById(int id);

    // Check if the specified product exists with given ID
    void verifyProductExists(int productId);

    boolean hasProductsInCategory(int categoryId);
}
