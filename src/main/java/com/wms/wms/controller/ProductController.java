package com.wms.wms.controller;

import com.wms.wms.dto.request.product.ProductRequest;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.dto.response.product.ProductResponse;
import com.wms.wms.service.product.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    // Get list of all product
    @GetMapping("")
    public ResponseEntity<?> findAll() {
        log.info("Request get product list");
        List<ProductResponse> response = productService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get product list successfully", response);
    }

//    @PostMapping("/products")
//    public ResponseEntity addProduct(@RequestParam(required = false, name = "image") MultipartFile image,
//                                     @RequestParam("model") String model) {
//        log.info("Request add product ");
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            ProductRequest request = mapper.readValue(model, ProductRequest.class);
//            request.setImage(image);
//            request.setId(0);
//            ProductDetailResponse response = productService.save(request);
//            return new ResponseSuccess(HttpStatus.OK, "Create product successfully",response);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PutMapping("/products")
//    public ResponseEntity updateProduct(@RequestParam(required = false, name = "image") MultipartFile image,
//                                        @RequestParam("model") String model) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            ProductRequest request = mapper.readValue(model, ProductRequest.class);
//            request.setImage(image);
//            log.info("Request update product id: {}", request.getId());
//            ProductDetailResponse response = productService.save(request);
//            return new ResponseSuccess(HttpStatus.OK, "Update product successfully",response);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("")
    public ResponseEntity<?> addProduct(@RequestBody @Valid ProductRequest requestDTO) {
        log.info("Request add product");
        requestDTO.setId(0L);
        ProductResponse response = productService.save(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Create product successfully",response);
    }

    @PutMapping("")
    public ResponseEntity<?> updateProduct(@RequestBody @Valid ProductRequest requestDTO) {
        log.info("Request update product id: {}", requestDTO.getId());
        ProductResponse response = productService.save(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Update product successfully",response);
    }

    // Get product by id
    @GetMapping("/{productId}")
    public ResponseEntity<?> findById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("productId") Long productId) {

        log.info("Get product detail id: {}", productId);
        ProductResponse response = productService.findById(productId);
        return new ResponseSuccess(HttpStatus.OK, "Get product detail successfully", response);
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("productId") Long productId) {

        log.info("Request delete product Id={}", productId);
        productService.deleteById(productId);
        return new ResponseSuccess(HttpStatus.OK, "Delete product successfully");
    }
}
