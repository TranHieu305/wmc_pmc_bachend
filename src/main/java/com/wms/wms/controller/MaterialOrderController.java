package com.wms.wms.controller;


import com.wms.wms.dto.request.MaterialOrderRequestDTO;
import com.wms.wms.dto.response.MaterialOrderResponse;
import com.wms.wms.dto.response.ResponseData;
import com.wms.wms.dto.response.ResponseError;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.service.IMaterialOrderService;
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
@CrossOrigin(origins = { "http://localhost:3000", "https://phucthanhwms.netlify.app" })
@RequestMapping("/api")
public class MaterialOrderController {
    private final IMaterialOrderService materialOrderService;

    private static final String ERROR_MESSAGE = "errorMessage={}";

    @GetMapping("/material-orders")
    public ResponseEntity<ResponseData> findAll() {
        log.info("Request get material order list");
        try {
            List<MaterialOrderResponse> response = materialOrderService.findAll();
            return new ResponseSuccess(HttpStatus.OK, "Get material order list successfully", response);
        }
        catch (Exception exc) {
            log.error(ERROR_MESSAGE, exc.getMessage(), exc.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Get material order list fail");
        }
    }

    @GetMapping("/material-orders/{orderId}")
    public ResponseEntity<ResponseData> findById(@Min(value = 0, message = "Id must be greater than 0")
                                                 @PathVariable("orderId") int orderId) {
        log.info("Get Material order detail id: {}", orderId);
        try {
            MaterialOrderResponse response = materialOrderService.findById(orderId);
            return new ResponseSuccess(HttpStatus.OK, "Get material order detail successfully", response);
        }
        catch (Exception exception) {
            log.error(ERROR_MESSAGE, exception.getMessage(), exception.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Get material order detail fail");
        }
    }

    // Create new Material_order
    @PostMapping("/material-orders")
    public ResponseEntity<ResponseData> addMaterialOrder(@RequestBody @Valid MaterialOrderRequestDTO requestDTO) {
        log.info("Request add material order");
        try {
            MaterialOrderResponse response = materialOrderService.save(requestDTO);
            return new ResponseSuccess(HttpStatus.OK, "Create material order successfully",response);
        }
        catch (Exception exception) {
            log.error(ERROR_MESSAGE, exception.getMessage(), exception.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Create material order fail");
        }
    }

    @PutMapping("/material-orders/{orderId}")
    public ResponseEntity<ResponseData> updateMaterialOrder(@RequestBody @Valid MaterialOrderRequestDTO orderRequestDTO,
                                                            @Min(value = 0, message = "Id must be greater than 0")
                                                            @PathVariable("orderId") int orderId) {
        log.info("Request update orderId={}", orderId);
        try {
            MaterialOrderResponse response = materialOrderService.update(orderRequestDTO, orderId);
            return new ResponseSuccess(HttpStatus.OK, "Update material order successfully", response);
        }
        catch (Exception exc) {
            log.error(ERROR_MESSAGE, exc.getMessage(), exc.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Update material order fail");
        }
    }

    @DeleteMapping("/material-orders/{orderId}")
    public ResponseEntity<ResponseData> deleteById( @Min(value = 0, message = "Id must be greater than 0")
                                                    @PathVariable("orderId") int orderId) {
        log.info("Request delete material order Id={}", orderId);
        try {
            materialOrderService.deleteById(orderId);
            return new ResponseSuccess(HttpStatus.OK, "Delete material order successfully");
        }
        catch (Exception exc) {
            log.error(ERROR_MESSAGE, exc.getMessage(), exc.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST, "Delete material order fail");
        }
    }

}
