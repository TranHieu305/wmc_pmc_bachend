package com.wms.wms.controller;

import com.wms.wms.dto.request.ProductPriceRequest;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.entity.ProductPrice;
import com.wms.wms.service.IProductPriceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/product-prices")
public class ProductPriceController {
    private final IProductPriceService productPriceService;

    @PostMapping("")
    public ResponseEntity addProductPrice(@RequestBody @Valid ProductPriceRequest request) {
        log.info("Request add product price");
        request.setId(0);
        ProductPrice response = productPriceService.save(request);
        return new ResponseSuccess(HttpStatus.OK, "Create product successfully",response);
    }

    @GetMapping("")
    public ResponseEntity findAll() {
        log.info("Request get all product prices ");
        List<ProductPrice> response = productPriceService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get all product prices successfully", response);
    }

    @GetMapping("price/{productId}")
    public ResponseEntity findByProductId( @Min(value = 1, message = "Id must be greater than 0")
                                           @PathVariable("productId") int productId) {
        log.info("Request get prices of product ID: {}", productId);
        List<ProductPrice> response = productPriceService.findPricesByProductId(productId);
        return new ResponseSuccess(HttpStatus.OK, "Get product prices successfully", response );

    }
}
