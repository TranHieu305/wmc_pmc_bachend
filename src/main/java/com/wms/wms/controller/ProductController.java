//package com.wms.wms.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.wms.wms.dto.request.ProductRequest;
//import com.wms.wms.dto.response.product.ProductDetailResponse;
//import com.wms.wms.dto.response.product.ProductGeneralResponse;
//import com.wms.wms.dto.response.ResponseSuccess;
//import com.wms.wms.service.ProductService;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.Min;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@Slf4j
//@RestController
//@RequestMapping("/api")
//public class ProductController {
//    private final ProductService productService;
//
//    public ProductController(ProductService productService) {
//        this.productService = productService;
//    }
//
//    // Get list of all product
//    @GetMapping("/products")
//    public ResponseEntity findAll() {
//        log.info("Request get product list");
//        List<ProductGeneralResponse> response = productService.findAll();
//        return new ResponseSuccess(HttpStatus.OK, "Get product list successfully", response);
//    }
//
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
//
//    // Get product by id
//    @GetMapping("/products/{productId}")
//    public ResponseEntity findById(
//            @Min(value = 1, message = "Id must be greater than 0")
//            @PathVariable("productId") int productId) {
//
//        log.info("Get product detail id: {}", productId);
//        ProductDetailResponse response = productService.findById(productId);
//        return new ResponseSuccess(HttpStatus.OK, "Get product detail successfully", response);
//    }
//
////    @GetMapping("/products/find-by-name")
////    public ResponseEntity<ResponseData> findByName(@RequestParam String name) {
////        log.info("Get product category detail name: {}", name);
////        List<ProductCategoryDetailResponse> response = categoryService.findByName(name);
////        return new ResponseSuccess(HttpStatus.OK, "Get product category by name successfully", response);
////    }
//
//    @DeleteMapping("/products/{productId}")
//    public ResponseEntity deleteById(
//            @Min(value = 1, message = "Id must be greater than 0")
//            @PathVariable("productId") int productId) {
//
//        log.info("Request delete product Id={}", productId);
//        productService.deleteById(productId);
//        return new ResponseSuccess(HttpStatus.OK, "Delete product successfully");
//    }
//}
