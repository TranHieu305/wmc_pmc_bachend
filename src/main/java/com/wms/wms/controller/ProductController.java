package com.wms.wms.controller;

import com.wms.wms.dto.request.ProductRequestDTO;
import com.wms.wms.dto.response.product.ProductDetailResponse;
import com.wms.wms.dto.response.product.ProductGeneralResponse;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.service.IProductService;
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
public class ProductController {
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    // Get list of all product
    @GetMapping("/products")
    public ResponseEntity findAll() {
        log.info("Request get product list");
        List<ProductGeneralResponse> response = productService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get product list successfully", response);
    }

    @PostMapping("/products")
    public ResponseEntity addProduct(@RequestBody @Valid ProductRequestDTO requestDTO) {
        log.info("Request add product ");
        requestDTO.setId(0);
        ProductDetailResponse response = productService.save(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Create product successfully",response);
    }

    @PutMapping("/products")
    public ResponseEntity updateProduct(@RequestBody @Valid ProductRequestDTO requestDTO) {
        log.info("Request update product id: {}", requestDTO.getId());
        ProductDetailResponse response = productService.save(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Update product successfully",response);
    }

    // Get product by id
    @GetMapping("/products/{productId}")
    public ResponseEntity findById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("productId") int productId) {

        log.info("Get product detail id: {}", productId);
        ProductDetailResponse response = productService.findById(productId);
        return new ResponseSuccess(HttpStatus.OK, "Get product detail successfully", response);
    }

//    @GetMapping("/products/find-by-name")
//    public ResponseEntity<ResponseData> findByName(@RequestParam String name) {
//        log.info("Get product category detail name: {}", name);
//        List<ProductCategoryDetailResponse> response = categoryService.findByName(name);
//        return new ResponseSuccess(HttpStatus.OK, "Get product category by name successfully", response);
//    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity deleteById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("productId") int productId) {

        log.info("Request delete product Id={}", productId);
        productService.deleteById(productId);
        return new ResponseSuccess(HttpStatus.OK, "Delete product successfully");
    }
}
