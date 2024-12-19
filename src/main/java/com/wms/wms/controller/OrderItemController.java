package com.wms.wms.controller;

import com.wms.wms.dto.request.order.OrderItemUpdateRequest;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.dto.response.order.OrderResponse;
import com.wms.wms.service.order.OrderItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order-items")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @PatchMapping("")
    public ResponseEntity<?> updateItem(@RequestBody @Valid OrderItemUpdateRequest requestDTO) {
        log.info("Request update order item");
        orderItemService.updateItem(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Update order item successfully");
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> deleteById(
            @Min(value = 1, message = "Id must be greater than 0")
            @PathVariable("itemId") Long itemId) {

        log.info("Request delete order item Id={}", itemId);
        orderItemService.deleteItem(itemId);
        return new ResponseSuccess(HttpStatus.OK, "Delete order item successfully");
    }
}
