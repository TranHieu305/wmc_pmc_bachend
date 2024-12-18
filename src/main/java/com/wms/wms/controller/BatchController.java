package com.wms.wms.controller;

import com.wms.wms.dto.request.batch.BatchRequest;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.dto.response.batch.BatchResponse;
import com.wms.wms.service.batch.BatchService;
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
@RequestMapping("/api/batches")
public class BatchController {
    private final BatchService batchService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        log.info("Request get batch list");
        List<BatchResponse> response = batchService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get batch list successfully", response);
    }

    @GetMapping("/{batchId}")
    public ResponseEntity<?> findById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("batchId") Long batchId) {

        log.info("Get batch detail id: {}", batchId);
        BatchResponse response = batchService.findById(batchId);
        return new ResponseSuccess(HttpStatus.OK, "Get batch detail successfully", response);
    }

    @DeleteMapping("/{batchId}")
    public ResponseEntity<?> deleteById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("batchId") Long batchId) {

        log.info("Request delete batch Id={}", batchId);
        batchService.deleteById(batchId);
        return new ResponseSuccess(HttpStatus.OK, "Delete batch successfully");
    }

    @PostMapping("")
    public ResponseEntity<?> addBatch(@RequestBody @Valid BatchRequest requestDTO) {
        log.info("Request add batch");
        requestDTO.setId(0L);
        BatchResponse response = batchService.create(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Create batch successfully",response);
    }

    @PatchMapping("/{batchId}/status/delivered")
    public ResponseEntity<?> markAsDelivered(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("batchId") Long batchId) {
        log.info("Request to mark batch as DELIVERED, Id={}", batchId);
        batchService.markAsDelivered(batchId);
        return new ResponseSuccess(HttpStatus.OK, "Batch marked as DELIVERED successfully");
    }
}
