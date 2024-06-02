package com.wms.wms.controller;

import com.wms.wms.dto.request.ProductWarehouseRequest;
import com.wms.wms.dto.response.ResponseData;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.dto.response.product.ProductWarehouseResponse;
import com.wms.wms.service.ProductWarehouseService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/product-warehouses")
public class ProductWarehouseController {
    private final ProductWarehouseService productWarehouseService;

    public ProductWarehouseController(ProductWarehouseService productWarehouseService) {
        this.productWarehouseService = productWarehouseService;
    }

    @GetMapping("")
    public ResponseEntity<ResponseData> findAll() {
        log.info("Request get all product warehouse");
        List<ProductWarehouseResponse> response = productWarehouseService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get all product warehouse successfully", response);
    }

    @PostMapping("")
    public ResponseEntity<ResponseData> addProductWarehouse(@RequestBody @Valid ProductWarehouseRequest requestDTO) {
        log.info("Request add product warehouse");
        requestDTO.setId(0);
        ProductWarehouseResponse response = productWarehouseService.save(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Create product warehouse successfully",response);
    }

    @PutMapping("")
    public ResponseEntity<ResponseData> updateProductCategory(@RequestBody @Valid ProductWarehouseRequest requestDTO) {
        log.info("Request update product warehouse id: {}", requestDTO.getId());
        ProductWarehouseResponse response = productWarehouseService.save(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Update product warehouse successfully",response);
    }
}

