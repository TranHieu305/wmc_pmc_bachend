package com.wms.wms.controller;

import com.wms.wms.dto.request.produceditem.ProducedItemRequest;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.entity.ProducedItem;
import com.wms.wms.service.produceditem.ProducedItemService;
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
@RequestMapping("/api/produced-items")
public class ProducedItemController {
    private final ProducedItemService producedItemService;

    @GetMapping("/batch/{batchId}")
    public ResponseEntity<?> findByBatchId(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("batchId") Long batchId) {

        log.info("Get produced item by batch id: {}", batchId);
        List<ProducedItem> response = producedItemService.findByBatchId(batchId);
        return new ResponseSuccess(HttpStatus.OK, "Get produced item by batch successfully", response);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid ProducedItemRequest requestDTO) {
        log.info("Request create produced item");
        requestDTO.setId(0L);
        ProducedItem response = producedItemService.create(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Create produced item successfully",response);
    }

    @PatchMapping("/{itemId}/approve")
    public ResponseEntity<?> approve(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("itemId") Long itemId) {
        log.info("Request to approve produced item Id={}", itemId);
        producedItemService.approve(itemId);
        return new ResponseSuccess(HttpStatus.OK, "Approved produced item successfully");
    }

    @PatchMapping("/{itemId}/reject")
    public ResponseEntity<?> reject(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("itemId") Long itemId) {
        log.info("Request to reject produced item Id={}", itemId);
        producedItemService.reject(itemId);
        return new ResponseSuccess(HttpStatus.OK, "Rejected produced item successfully");
    }
}
