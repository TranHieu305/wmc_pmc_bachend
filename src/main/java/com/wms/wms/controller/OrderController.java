package com.wms.wms.controller;

import com.wms.wms.dto.request.order.OrderItemRequest;
import com.wms.wms.dto.request.order.OrderRequest;
import com.wms.wms.dto.request.order.OrderUpdateRequest;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.dto.response.order.OrderResponse;
import com.wms.wms.service.order.OrderService;
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
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        log.info("Request get order list");
        List<OrderResponse> response = orderService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get order list successfully", response);
    }

    @PostMapping("")
    public ResponseEntity<?> addOrder(@RequestBody @Valid OrderRequest requestDTO) {
        log.info("Request add order");
        requestDTO.setId(0L);
        OrderResponse response = orderService.create(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Create order successfully",response);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody @Valid OrderUpdateRequest request) {
        log.info("Request update order");
        OrderResponse response = orderService.update(request);
        return new ResponseSuccess(HttpStatus.OK, "Order updated successfully", response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> findById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("orderId") Long orderId) {

        log.info("Get order detail id: {}", orderId);
        OrderResponse response = orderService.findById(orderId);
        return new ResponseSuccess(HttpStatus.OK, "Get order detail successfully", response);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("orderId") Long orderId) {

        log.info("Request delete order Id={}", orderId);
        orderService.deleteById(orderId);
        return new ResponseSuccess(HttpStatus.OK, "Delete order successfully");
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<?> addItem (
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("orderId") Long orderId,
            @RequestBody @Valid OrderItemRequest itemRequest) {
        log.info("Request add item to order Id = {}", orderId);
        orderService.addItem(orderId, itemRequest);
        return new ResponseSuccess(HttpStatus.OK, "Update order successfully");
    }
}
