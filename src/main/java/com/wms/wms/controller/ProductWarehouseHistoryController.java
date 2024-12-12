package com.wms.wms.controller;

import com.wms.wms.dto.request.product.ProductWarehouseHistoryRequest;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.dto.response.product.ProductWarehouseHistoryResponse;
import com.wms.wms.service.product.ProductWarehouseHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pw-histories")
public class ProductWarehouseHistoryController {
    private final ProductWarehouseHistoryService service;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        log.info("Request get all product warehouse histories");
        List<ProductWarehouseHistoryResponse> response = service.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get all product warehouse histories successfully", response);
    }

    @PostMapping("")
    public ResponseEntity<?> addPWH(@RequestBody @Valid ProductWarehouseHistoryRequest requestDTO) {
        log.info("Request add product warehouse history");
        requestDTO.setId(0L);
        ProductWarehouseHistoryResponse response = service.save(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Create product warehouse history successfully",response);
    }
}
