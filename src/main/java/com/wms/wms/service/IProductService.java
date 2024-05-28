package com.wms.wms.service;

import com.wms.wms.dto.request.ProductRequestDTO;
import com.wms.wms.dto.response.product.ProductDetailResponse;
import com.wms.wms.dto.response.product.ProductGeneralResponse;
import com.wms.wms.entity.Product;

import java.util.List;

public interface IProductService {
    // Create/ Update Product order
    ProductDetailResponse save(ProductRequestDTO requestDTO);

    // Find Product by ID
    ProductDetailResponse findById(int productId);

    // Find Product by  name
    List<ProductDetailResponse> findByName(String name);

    // Find all Product
    List<ProductGeneralResponse> findAll();

    // Delete Product by ID
    void deleteById(int id);

    Product getProductById(int productId);
}
