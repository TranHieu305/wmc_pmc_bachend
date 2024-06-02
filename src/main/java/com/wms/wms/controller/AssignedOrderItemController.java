package com.wms.wms.controller;

import com.wms.wms.dto.response.ResponseData;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.service.AssignedOrderItemService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assigned-order-items")
public class AssignedOrderItemController {
    private final  AssignedOrderItemService assignedOrderItemService;

    @PostMapping("/{itemId}/delivered")
    public ResponseEntity<ResponseData> changeStatusToDelivered(@Min(value = 0, message = "Id must be greater than 0")
                                                                @PathVariable("itemId") int itemId) {
        log.info("Request change status item Id={} to delivered", itemId);
        assignedOrderItemService.changeStatusToDelivered(itemId);
        return new ResponseSuccess(HttpStatus.OK, "Change status item to delivered successfully");
    }

    @PostMapping("/{itemId}/returned")
    public ResponseEntity<ResponseData> changeStatusToReturned(@Min(value = 0, message = "Id must be greater than 0")
                                                                @PathVariable("itemId") int itemId) {
        log.info("Request change status item Id={} to returned", itemId);
        assignedOrderItemService.changeStatusToReturned(itemId);
        return new ResponseSuccess(HttpStatus.OK, "Change status item to returned successfully");
    }
}
