//package com.wms.wms.controller;
//
//import com.wms.wms.dto.request.ProductPriceRequest;
//import com.wms.wms.dto.response.ResponseSuccess;
//import com.wms.wms.entity.ProductPrice;
//import com.wms.wms.service.ProductPriceService;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.Min;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Slf4j
//@RestController
//@AllArgsConstructor(onConstructor = @__(@Autowired))
//@RequestMapping("/api/product-prices")
//public class ProductPriceController {
//    private final ProductPriceService productPriceService;
//
//    @PostMapping("")
//    public ResponseEntity addProductPrice(@RequestBody @Valid ProductPriceRequest request) {
//        log.info("Request add product price");
//        request.setId(0);
//        ProductPrice response = productPriceService.save(request);
//        return new ResponseSuccess(HttpStatus.OK, "Create product successfully",response);
//    }
//
//    @GetMapping("")
//    public ResponseEntity findAll() {
//        log.info("Request get all product prices ");
//        List<ProductPrice> response = productPriceService.findAll();
//        return new ResponseSuccess(HttpStatus.OK, "Get all product prices successfully", response);
//    }
//
//    @GetMapping("/current-prices")
//    public ResponseEntity findAllCurrentPrices() {
//        log.info("Request get all current product prices ");
//        List<ProductPrice> response = productPriceService.findAllCurrentPrices();
//        return new ResponseSuccess(HttpStatus.OK, "Get all product current prices successfully", response);
//    }
//
//    @GetMapping("/current-prices/product/{productId}")
//    public ResponseEntity findCurrentPricesOfProduct(@Min(value = 1, message = "Id must be greater than 0")
//                                                         @PathVariable("productId") int productId) {
//        log.info("Request get all current product prices of product ID: {} ", productId);
//        List<ProductPrice> response = productPriceService.findCurrentPricesByProductId(productId);
//        return new ResponseSuccess(HttpStatus.OK, "Get all product current prices successfully", response);
//    }
//
//    @GetMapping("/current-prices/partner/{partnerId}")
//    public ResponseEntity findCurrentPricesOfPartner(@Min(value = 1, message = "Id must be greater than 0")
//                                                     @PathVariable("partnerId") int partnerId) {
//        log.info("Request get all current product prices if partner ID: {} ", partnerId);
//        List<ProductPrice> response = productPriceService.findCurrentPricesByPartnerId(partnerId);
//        return new ResponseSuccess(HttpStatus.OK, "Get all product current prices successfully", response);
//    }
//
//    @GetMapping("product/{productId}")
//    public ResponseEntity findByProductId( @Min(value = 1, message = "Id must be greater than 0")
//                                           @PathVariable("productId") int productId) {
//        log.info("Request get prices of product ID: {}", productId);
//        List<ProductPrice> response = productPriceService.findPricesByProductId(productId);
//        return new ResponseSuccess(HttpStatus.OK, "Get product prices successfully", response );
//    }
//
//    @PutMapping("")
//    public ResponseEntity update( @RequestBody @Valid ProductPriceRequest request) {
//        log.info("Request update price ID: {}", request.getId());
//        ProductPrice response = productPriceService.save(request);
//        return new ResponseSuccess(HttpStatus.OK, "Update successfully", response );
//    }
//
//    @DeleteMapping("/{priceId}")
//    public ResponseEntity deleteById( @Min(value = 1, message = "Id must be greater than 0")
//                                           @PathVariable("priceId") int priceId) {
//        log.info("Request delete price ID: {}", priceId);
//        productPriceService.deleteById(priceId);
//        return new ResponseSuccess(HttpStatus.OK, "Delete price successfully");
//    }
//}
