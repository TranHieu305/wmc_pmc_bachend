package com.wms.wms.controller;

import com.wms.wms.dto.request.ProductPriceRequest;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.entity.ProductPrice;
import com.wms.wms.service.IProductPriceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/product-prices/")
public class ProductPriceController {
    private final IProductPriceService productPriceService;

    @PostMapping("")
    public ResponseEntity addProductPrice(@RequestBody @Valid ProductPriceRequest request) {
        log.info("Request add product price");
        request.setId(0);
        ProductPrice response = productPriceService.save(request);
        return new ResponseSuccess(HttpStatus.OK, "Create product successfully",response);
    }
}
