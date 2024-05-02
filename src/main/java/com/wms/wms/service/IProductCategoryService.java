package com.wms.wms.service;

import com.wms.wms.dto.request.ProductCategoryRequestDTO;
import com.wms.wms.dto.response.ProductCategoryDetailResponse;

import java.util.List;

public interface IProductCategoryService {
    // Create/ Update Product Category order
    ProductCategoryDetailResponse save(ProductCategoryRequestDTO requestDTO);

    // Find Product Category by categoryId
    ProductCategoryDetailResponse findById(int categoryId);

    // Find Product Category by category name
    List<ProductCategoryDetailResponse> findByName(String name);

    // Find all Product Category
    List<ProductCategoryDetailResponse> findAll();

    // Delete Product Category by ID
    void deleteById(int id);

    // Check if the specified product category exists with given ID
    void verifyCategoryExists(int categoryId);
}
