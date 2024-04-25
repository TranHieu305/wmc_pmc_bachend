package com.wms.wms.service.impl;


import com.wms.wms.dao.IProductCategoryDAO;
import com.wms.wms.dto.request.ProductCategoryRequestDTO;
import com.wms.wms.dto.response.ProductCategoryDetailResponse;
import com.wms.wms.service.IProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductCategoryServiceImpl implements IProductCategoryService {
    private final IProductCategoryDAO productCategoryDAO;

    @Autowired
    public ProductCategoryServiceImpl(IProductCategoryDAO productCategoryDAO) {
        this.productCategoryDAO = productCategoryDAO;
    }

    @Override
    public ProductCategoryDetailResponse save(ProductCategoryRequestDTO requestDTO) {
        return null;
    }

    @Override
    public ProductCategoryDetailResponse update(ProductCategoryRequestDTO requestDTO) {
        return null;
    }

    @Override
    public ProductCategoryDetailResponse findById(int categoryId) {
        return null;
    }

    @Override
    public List<ProductCategoryDetailResponse> findAll() {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }
}
