package com.wms.wms.controller;

import com.wms.wms.dto.request.ProductCategoryRequestDTO;
import com.wms.wms.dto.response.*;
import com.wms.wms.dto.response.product.ProductCategoryDetailResponse;
import com.wms.wms.service.IProductCategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class ProductCategoryController {
    private final IProductCategoryService categoryService;

    public ProductCategoryController(IProductCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Get list of all product-categories
    @GetMapping("/product-categories")
    public ResponseEntity<ResponseData> findAllCategories() {
        log.info("Request get product-categories list");
        List<ProductCategoryDetailResponse> response = categoryService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get product-categories list successfully", response);
    }

    @PostMapping("/product-categories")
    public ResponseEntity<ResponseData> addProductCategory(@RequestBody @Valid ProductCategoryRequestDTO requestDTO) {
        log.info("Request add product category");
        requestDTO.setId(0);
        ProductCategoryDetailResponse response = categoryService.save(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Create product category successfully",response);
    }

    @PutMapping("/product-categories")
    public ResponseEntity<ResponseData> updateProductCategory(@RequestBody @Valid ProductCategoryRequestDTO requestDTO) {
        log.info("Request update product category id: {}", requestDTO.getId());
        ProductCategoryDetailResponse response = categoryService.save(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Update product category successfully",response);
    }

    // Get product-category by id
    @GetMapping("/product-categories/{categoryId}")
    public ResponseEntity<ResponseData> findById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("categoryId") int categoryId) {

        log.info("Get product category detail id: {}", categoryId);
        ProductCategoryDetailResponse response = categoryService.findById(categoryId);
        return new ResponseSuccess(HttpStatus.OK, "Get product category detail successfully", response);
    }

    @GetMapping("/product-categories/find-by-name")
    public ResponseEntity<ResponseData> findByName(@RequestParam String name) {
        log.info("Get product category detail name: {}", name);
        List<ProductCategoryDetailResponse> response = categoryService.findByName(name);
        return new ResponseSuccess(HttpStatus.OK, "Get product category by name successfully", response);
    }

    @DeleteMapping("/product-categories/{categoryId}")
    public ResponseEntity<ResponseData> deleteById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("categoryId") int categoryId) {

        log.info("Request delete product category Id={}", categoryId);
        categoryService.deleteById(categoryId);
        return new ResponseSuccess(HttpStatus.OK, "Delete product category successfully");
    }
}
