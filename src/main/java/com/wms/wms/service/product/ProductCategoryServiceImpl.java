package com.wms.wms.service.product;


import com.wms.wms.dto.request.product.ProductCategoryRequest;
import com.wms.wms.dto.response.product.ProductCategoryResponse;
import com.wms.wms.entity.ProductCategory;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.product.ProductCategoryRequestMapper;
import com.wms.wms.mapper.product.ProductCategoryResponseMapper;
import com.wms.wms.repository.ProductCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    @Transactional
    public ProductCategoryResponse save(ProductCategoryRequest requestDTO) {
        validateRequest(requestDTO);
        ProductCategory category = ProductCategoryRequestMapper.INSTANCE.toEntity(requestDTO);

        ProductCategory dbCategory = productCategoryRepository.save(category);
        log.info("Save Product category successfully with ID: {}", dbCategory.getId());
        return ProductCategoryResponseMapper.INSTANCE.toDto(dbCategory);
    }

    @Override
    public ProductCategoryResponse findById(Long categoryId) {
        ProductCategory dbCategory = this.getCategoryById(categoryId);
        log.info("Get Product category detail ID: {} successfully ", categoryId);
        return ProductCategoryResponseMapper.INSTANCE.toDto(dbCategory);
    }

//    @Override
//    public List<ProductCategoryDetailResponse> findByName(String name) {
//        List<ProductCategory> categories = productCategoryRepository.findByName(name);
//        log.info("Get Product category detail by name: {} successfully ", name);
//        return categories.stream().map(this::convertToDetailResponse).toList();
//    }

    @Override
    public List<ProductCategoryResponse> findAll() {
        List<ProductCategory> categorieList = productCategoryRepository.findAll();
        log.info("Get Product category list successfully");
        return ProductCategoryResponseMapper.INSTANCE.toDtoList(categorieList);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        ProductCategory category = this.getCategoryById(id);

//        checkConstraintToDelete(id);
        productCategoryRepository.delete(category);
        log.info("Delete Product category ID: {} successfully", id);
    }

    public ProductCategory getCategoryById(Long id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException ("No Product category exists with the given Id: " + id));
    }

    private void validateRequest(ProductCategoryRequest requestDTO) {
        // validate ID
        ProductCategory existingCategory = null;
        if (requestDTO.getId() != 0) {
            existingCategory = this.getCategoryById(requestDTO.getId());
        }
//        if (existingCategory == null) {
//            checkUniqueNameForNew(requestDTO.getName());
//        }
//        else  {
//            checkUniqueNameForUpdate(requestDTO.getName(), existingCategory.getName());
//        }
    }

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
}
