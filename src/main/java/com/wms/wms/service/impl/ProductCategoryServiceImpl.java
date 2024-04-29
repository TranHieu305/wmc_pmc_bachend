package com.wms.wms.service.impl;


import com.wms.wms.dao.IProductCategoryDAO;
import com.wms.wms.dto.request.ProductCategoryRequestDTO;
import com.wms.wms.dto.response.ProductCategoryDetailResponse;
import com.wms.wms.entity.ProductCategory;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.exception.UniqueConstraintViolationException;
import com.wms.wms.mapper.productcategory.ProductCategoryRequestMapper;
import com.wms.wms.mapper.productcategory.ProductCategoryResponseMapper;
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
        checkUniqueConstraints(requestDTO);
        if (requestDTO.getId() != 0) {
            ProductCategory category = getCategoryById(requestDTO.getId()); // Check if category with given ID exists
        }

        ProductCategory category = ProductCategoryRequestMapper.INSTANCE.requestToCategory(requestDTO);
        ProductCategory dbCategory = productCategoryDAO.save(category);
        log.info("Save Product category successfully with ID: {}", dbCategory.getId());
        return ProductCategoryResponseMapper.INSTANCE.categoryToResponse(dbCategory);
    }

    @Override
    public ProductCategoryDetailResponse findById(int categoryId) {
        ProductCategory dbCategory = getCategoryById(categoryId);
        log.info("Get Product category detail ID: {} successfully ", categoryId);
        return ProductCategoryResponseMapper.INSTANCE.categoryToResponse(dbCategory);
    }

    @Override
    public List<ProductCategoryDetailResponse> findAll() {
        List<ProductCategory> categorieList = productCategoryDAO.findAll();
        List<ProductCategoryDetailResponse> categoryDetailResponseList = categorieList.stream().map(
                ProductCategoryResponseMapper.INSTANCE::categoryToResponse
        ).toList();
        log.info("Get Product category list successfully");
        return categoryDetailResponseList;
    }

    @Override
    public void deleteById(int id) {
        ProductCategory category = getCategoryById(id);
        productCategoryDAO.deleteById(category);
        log.info("Delete Product category ID: {} successfully", id);
    }

    @Override
    public void verifyCategoryExists(int categoryId) {
        getCategoryById(categoryId);
    }

    /**
     * Retrieves the Product Category with the given ID from the database.
     * Throws a ResourceNotFoundException if the supplier does not exist.
     *
     * @param id Product category ID
     * @return ProductCategory entity from Database
     */
    private ProductCategory getCategoryById(int id) {
        ProductCategory category = productCategoryDAO.findById(id);
        if (category == null) {
            throw new ResourceNotFoundException ("No Product category exists with the given Id: " + id);
        }
        return category;
    }


    /**
     * Checks if the provided product category request DTO satisfies unique constraints.
     *
     * @param requestDTO The product category request DTO to validate.
     * @throws UniqueConstraintViolationException If any unique constraint is violated.
     */
    private void checkUniqueConstraints(ProductCategoryRequestDTO requestDTO) {
        checkUniqueName(requestDTO.getName());
    }

    /**
     * Checks if the provided product category name is unique.
     *
     * @param categoryName Product category name
     * @throws UniqueConstraintViolationException If a category with the same name already exists.
     */
    private void checkUniqueName(String categoryName) {
        List<ProductCategory> dbCategoryList = productCategoryDAO.findByName(categoryName);
        if (!dbCategoryList.isEmpty()) {
            throw new UniqueConstraintViolationException("Product category name must be unique");
        }
    }

}
