//package com.wms.wms.controller;
//
//
//import com.wms.wms.dto.request.MaterialOrderRequest;
//import com.wms.wms.dto.request.OrderStatusRequest;
//import com.wms.wms.dto.response.order.MaterialOrderDetailResponse;
//import com.wms.wms.dto.response.ResponseData;
//import com.wms.wms.dto.response.ResponseSuccess;
//import com.wms.wms.service.MaterialOrderService;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.Min;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api")
//public class MaterialOrderController {
//    private final MaterialOrderService materialOrderService;
//
//    @GetMapping("/material-orders")
//    public ResponseEntity<ResponseData> findAll() {
//        log.info("Request get material order list");
//        List<MaterialOrderDetailResponse> response = materialOrderService.findAll();
//        return new ResponseSuccess(HttpStatus.OK, "Get material order list successfully", response);
//    }
//
//    // Get Material Order by iD
//    @GetMapping("/material-orders/{orderId}")
//    public ResponseEntity<ResponseData> findById(@Min(value = 0, message = "Id must be greater than 0")
//                                                 @PathVariable("orderId") int orderId) {
//        log.info("Get Material order detail id: {}", orderId);
//        MaterialOrderDetailResponse response = materialOrderService.findById(orderId);
//        return new ResponseSuccess(HttpStatus.OK, "Get material order detail successfully", response);
//    }
//
//    // Create new Material Order
//    @PostMapping("/material-orders")
//    public ResponseEntity<ResponseData> addMaterialOrder(@RequestBody @Valid MaterialOrderRequest requestDTO) {
//        log.info("Request add material order");
//        MaterialOrderDetailResponse response = materialOrderService.save(requestDTO);
//        return new ResponseSuccess(HttpStatus.OK, "Create material order successfully",response);
//
//    }
//
//    // Update Material order
//    @PutMapping("/material-orders")
//    public ResponseEntity<ResponseData> updateMaterialOrder(@RequestBody @Valid MaterialOrderRequest orderRequestDTO,
//                                                            @Min(value = 0, message = "Id must be greater than 0")
//                                                            @PathVariable("orderId") int orderId) {
//        log.info("Request update orderId={}", orderId);
//        MaterialOrderDetailResponse response = materialOrderService.save(orderRequestDTO);
//        return new ResponseSuccess(HttpStatus.OK, "Update material order successfully", response);
//    }
//
//    // Update Material Order status
//    @PutMapping("/material-orders/{orderId}/status")
//    public  ResponseEntity<ResponseData> updateStatusMaterialOrder(@RequestBody @Valid OrderStatusRequest statusRequest,
//                                                                   @Min(value = 0, message = "Id must be greater than 0")
//                                                                   @PathVariable("orderId") int orderId) {
//        log.info("Request update status orderId={}", orderId);
//        materialOrderService.updateOrderStatus(orderId, statusRequest);
//        return new ResponseSuccess(HttpStatus.OK, "Update material order status successfully");
//    }
//
//    // Delete Material Order
//    @DeleteMapping("/material-orders/{orderId}")
//    public ResponseEntity<ResponseData> deleteById( @Min(value = 0, message = "Id must be greater than 0")
//                                                    @PathVariable("orderId") int orderId) {
//        log.info("Request delete material order Id={}", orderId);
//        materialOrderService.deleteById(orderId);
//        return new ResponseSuccess(HttpStatus.OK, "Delete material order successfully");
//    }
//
//}
