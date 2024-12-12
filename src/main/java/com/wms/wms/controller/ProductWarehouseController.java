package com.wms.wms.controller;

import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.dto.response.product.ProductWarehouseResponse;
import com.wms.wms.service.product.ProductWarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product-warehouses")
public class ProductWarehouseController {
    private final ProductWarehouseService productWarehouseService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        log.info("Request get all product warehouse");
        List<ProductWarehouseResponse> response = productWarehouseService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get all product warehouse successfully", response);
    }
}

