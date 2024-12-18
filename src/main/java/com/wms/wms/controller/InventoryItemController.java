package com.wms.wms.controller;

import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.entity.InventoryItem;
import com.wms.wms.service.inventoryitem.InventoryItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory-items")
public class InventoryItemController {
    private final InventoryItemService inventoryItemService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        log.info("Request get all inventory items");
        List<InventoryItem> response = inventoryItemService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get all inventory items successfully", response);
    }
}
