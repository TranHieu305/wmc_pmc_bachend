package com.wms.wms.service;

import com.wms.wms.dto.request.ProductCategoryRequestDTO;
import com.wms.wms.dto.response.ProductCategoryDetailResponse;

import java.util.List;

public interface IProductCategoryService {
    // Add new Product Category order
    ProductCategoryDetailResponse save(ProductCategoryRequestDTO requestDTO);

    // Update Product Category
    ProductCategoryDetailResponse update(ProductCategoryRequestDTO requestDTO);

    // Find Product Category by categoryId
    ProductCategoryDetailResponse findById(int categoryId);

    // Find all Product Category
    List<ProductCategoryDetailResponse> findAll();

    // Delete Product Category by Id
    void deleteById(int id);
}
