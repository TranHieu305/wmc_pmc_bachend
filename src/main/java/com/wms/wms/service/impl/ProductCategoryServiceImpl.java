//package com.wms.wms.service.impl;
//
//
//import com.wms.wms.dto.request.ProductCategoryRequest;
//import com.wms.wms.dto.response.product.ProductCategoryDetailResponse;
//import com.wms.wms.entity.Product;
//import com.wms.wms.entity.ProductCategory;
//import com.wms.wms.exception.ConstraintViolationException;
//import com.wms.wms.exception.ResourceNotFoundException;
//import com.wms.wms.exception.UniqueConstraintViolationException;
//import com.wms.wms.repository.ProductCategoryRepository;
//import com.wms.wms.repository.ProductRepository;
//import com.wms.wms.service.ProductCategoryService;
//import com.wms.wms.util.StringHelper;
//import jakarta.transaction.Transactional;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Slf4j
//@Service
//@AllArgsConstructor(onConstructor = @__(@Autowired))
//public class ProductCategoryServiceImpl implements ProductCategoryService {
//    private final ProductCategoryRepository productCategoryRepository;
//    private final ProductRepository productRepository;
//
//    @Override
//    @Transactional
//    public ProductCategoryDetailResponse save(ProductCategoryRequest requestDTO) {
//        validate(requestDTO);
//        ProductCategory category;
//        if (requestDTO.getId() != 0) {
//            category = this.getCategory(requestDTO.getId());
//        }
//        else {
//            category = ProductCategory.builder()
//                    .productType(requestDTO.getProductType())
//                    .build();
//        }
//        category.setName(StringHelper.preProcess(requestDTO.getName()));
//        category.setDescription(StringHelper.preProcess(requestDTO.getDescription()));
//
//        ProductCategory dbCategory = productCategoryRepository.save(category);
//        log.info("Save Product category successfully with ID: {}", dbCategory.getId());
//        return this.convertToDetailResponse(dbCategory);
//    }
//
//    @Override
//    public ProductCategoryDetailResponse findById(int categoryId) {
//        ProductCategory dbCategory = this.getCategory(categoryId);
//        log.info("Get Product category detail ID: {} successfully ", categoryId);
//        return this.convertToDetailResponse(dbCategory);
//    }
//
//    @Override
//    public List<ProductCategoryDetailResponse> findByName(String name) {
//        List<ProductCategory> categories = productCategoryRepository.findByName(name);
//        log.info("Get Product category detail by name: {} successfully ", name);
//        return categories.stream().map(this::convertToDetailResponse).toList();
//    }
//
//    @Override
//    public List<ProductCategoryDetailResponse> findAll() {
//        List<ProductCategory> categorieList = productCategoryRepository.findAll();
//        List<ProductCategoryDetailResponse> categoryDetailResponseList = categorieList.stream()
//                .map(this::convertToDetailResponse)
//                .toList();
//        log.info("Get Product category list successfully");
//        return categoryDetailResponseList;
//    }
//
//    @Override
//    @Transactional
//    public void deleteById(int id) {
//        ProductCategory category = this.getCategory(id);
//
//        checkConstraintToDelete(id);
//        productCategoryRepository.delete(category);
//        log.info("Delete Product category ID: {} successfully", id);
//    }
//
//    @Override
//    public void verifyCategoryExists(int categoryId) {
//        this.getCategory(categoryId);
//    }
//
//
//    @Override
//    public ProductCategory getCategory(int id) {
//        return productCategoryRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException ("No Product category exists with the given Id: " + id));
//    }
//
//    private void validate(ProductCategoryRequest requestDTO) {
//        // validate ID
//        ProductCategory existingCategory = null;
//        if (requestDTO.getId() != 0) {
//            existingCategory = this.getCategory(requestDTO.getId());
//        }
//        if (existingCategory == null) {
//            checkUniqueNameForNew(requestDTO.getName());
//        }
//        else  {
//            checkUniqueNameForUpdate(requestDTO.getName(), existingCategory.getName());
//        }
//    }
//
//    /**
//     * Checks if the provided product category name is unique.
//     *
//     * @param categoryName Product category name
//     * @throws UniqueConstraintViolationException If a category with the same name already exists.
//     */
//    private void checkUniqueNameForNew(String categoryName) {
//        String cleanName = StringHelper.preProcess(categoryName);
//        List<ProductCategory> dbCategoryList = productCategoryRepository.findByName(cleanName);
//        if (!dbCategoryList.isEmpty()) {
//            throw new UniqueConstraintViolationException("A product category with the name '" + cleanName + "' already exists.");
//        }
//    }
//
//    private void checkUniqueNameForUpdate(String newName, String existingName) {
//        String cleanNewName = StringHelper.preProcess(newName);
//        if (cleanNewName.equals(existingName)) {
//            return;
//        }
//        List<ProductCategory> dbCategoryList = productCategoryRepository.findByName(cleanNewName);
//        if (!dbCategoryList.isEmpty()) {
//            throw new UniqueConstraintViolationException("A product category with the name '" + cleanNewName + "' already exists.");
//        }
//    }
//
//    private void checkConstraintToDelete(int categoryId) {
//        List<Product> products = productRepository.findByProductCategoryId(categoryId);
//        if (!products.isEmpty()) {
//            throw new ConstraintViolationException("Cannot delete category. Associated products exist.");
//        }
//    }
//
//    private ProductCategoryDetailResponse convertToDetailResponse(ProductCategory category) {
//        return ProductCategoryDetailResponse.builder()
//                .id(category.getId())
//                .name(category.getName())
//                .description(category.getDescription())
//                .productType(category.getProductType())
//                .createdAt(category.getCreatedAt())
//                .modifiedAt(category.getModifiedAt())
//                .build();
//    }
//}
