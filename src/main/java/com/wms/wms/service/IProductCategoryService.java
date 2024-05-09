package com.wms.wms.service;

import com.wms.wms.dto.request.ProductCategoryRequestDTO;
import com.wms.wms.dto.response.ProductCategoryDetailResponse;

import java.util.List;

public interface IProductCategoryService {

    ProductCategoryDetailResponse save(ProductCategoryRequestDTO requestDTO);

    ProductCategoryDetailResponse findById(int categoryId);

    List<ProductCategoryDetailResponse> findByName(String name);

    List<ProductCategoryDetailResponse> findAll();

    void deleteById(int id);

    void verifyCategoryExists(int categoryId);
}
