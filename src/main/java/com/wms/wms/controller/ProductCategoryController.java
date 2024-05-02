package com.wms.wms.controller;

import com.wms.wms.dto.request.ProductCategoryRequestDTO;
import com.wms.wms.dto.response.*;
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
@CrossOrigin(origins = { "http://localhost:3000", "https://phucthanhwms.netlify.app" })
@RequestMapping("/api")
public class ProductCategoryController {
    private final IProductCategoryService categoryService;
    private static final String ERROR_MESSAGE = "errorMessage={}";

    public ProductCategoryController(IProductCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Get list of all product-categories
    @GetMapping("/product-categories")
    public ResponseEntity<ResponseData> findAllCategories() {
        log.info("Request get product-categories list");
        try {
            List<ProductCategoryDetailResponse> response = categoryService.findAll();
            return new ResponseSuccess(HttpStatus.OK, "Get product-categories list successfully", response);
        }
        catch (Exception exc) {
            log.error(ERROR_MESSAGE, exc.getMessage(), exc.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Get product-categories list fail");
        }
    }

    @PostMapping("/product-categories")
    public ResponseEntity<ResponseData> addProductCategory(@RequestBody @Valid ProductCategoryRequestDTO requestDTO) {
        log.info("Request add product category");
        try {
            requestDTO.setId(0);
            ProductCategoryDetailResponse response = categoryService.save(requestDTO);
            return new ResponseSuccess(HttpStatus.OK, "Create product category successfully",response);
        }
        catch (Exception exception) {
            log.error(ERROR_MESSAGE, exception.getMessage(), exception.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Create product category fail");
        }
    }

    @PutMapping("/product-categories")
    public ResponseEntity<ResponseData> updateProductCategory(@RequestBody @Valid ProductCategoryRequestDTO requestDTO) {
        log.info("Request update product category id: {}", requestDTO.getId());
        try {
            ProductCategoryDetailResponse response = categoryService.save(requestDTO);
            return new ResponseSuccess(HttpStatus.OK, "Update product category successfully",response);
        }
        catch (Exception exception) {
            log.error(ERROR_MESSAGE, exception.getMessage(), exception.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Update product category fail");
        }
    }

    // Get product-category by id
    @GetMapping("/product-categories/{categoryId}")
    public ResponseEntity<ResponseData> findById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("categoryId") int categoryId) {

        log.info("Get product category detail id: {}", categoryId);
        try {
            ProductCategoryDetailResponse response = categoryService.findById(categoryId);
            return new ResponseSuccess(HttpStatus.OK, "Get product category detail successfully", response);
        }
        catch (Exception exception) {
            log.error(ERROR_MESSAGE, exception.getMessage(), exception.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Get product category detail fail");
        }
    }

    @GetMapping("/product-categories/find-by-name")
    public ResponseEntity<ResponseData> findByName(@RequestParam String name) {
        log.info("Get product category detail name: {}", name);
        try {
            List<ProductCategoryDetailResponse> response = categoryService.findByName(name);
            return new ResponseSuccess(HttpStatus.OK, "Get product category by name successfully", response);
        }
        catch (Exception exception) {
            log.error(ERROR_MESSAGE, exception.getMessage(), exception.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Get product category by name fail");
        }
    }

    @DeleteMapping("/product-categories/{categoryId}")
    public ResponseEntity<ResponseData> deleteById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("categoryId") int categoryId) {

        log.info("Request delete product category Id={}", categoryId);
        try {
            categoryService.deleteById(categoryId);
            return new ResponseSuccess(HttpStatus.OK, "Delete product category successfully");
        }
        catch (Exception exc) {
            log.error(ERROR_MESSAGE, exc.getMessage(), exc.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Delete product category fail");
        }
    }
}
