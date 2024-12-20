package com.wms.wms.controller;

import com.wms.wms.dto.request.batch.BatchItemUpdateRequest;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.entity.BatchItem;
import com.wms.wms.service.batch.BatchItemService;
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
@RequestMapping("/api/batch-items")
public class BatchItemController {
    private final BatchItemService batchItemService;

    @PatchMapping("")
    public ResponseEntity<?> updateItem(@RequestBody @Valid BatchItemUpdateRequest requestDTO) {
        log.info("Request update batch item");
        batchItemService.updateItem(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Update batch item successfully");
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> deleteById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("itemId") Long itemId) {

        log.info("Request delete batch item Id={}", itemId);
        batchItemService.deleteItem(itemId);
        return new ResponseSuccess(HttpStatus.OK, "Delete batch item successfully");
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> findByProductId(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("productId") Long productId) {

        log.info("Request find batch items by product Id={}", productId);
        List<BatchItem> itemList =  batchItemService.findByProductId(productId);
        return new ResponseSuccess(HttpStatus.OK, "Find batch items by productId successfully", itemList);
    }

    @PatchMapping("/batch/{batchId}/item/{itemId}/status/completed")
    public ResponseEntity<?> markAsCompleted(
            @Min(value = 1, message = "Id batch must be greater than 0")
            @PathVariable("batchId") Long batchId,
            @Min(value = 1, message = "Id item must be greater than 0")
            @PathVariable("itemId") Long itemId) {
        log.info("Request to mark batch item as COMPLETED, Id={}", itemId);
        batchItemService.markAsComplete(itemId, batchId);
        return new ResponseSuccess(HttpStatus.OK, "Batch item marked as COMPLETED successfully");
    }
}
