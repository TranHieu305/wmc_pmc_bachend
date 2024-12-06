//package com.wms.wms.controller;
//
//import com.wms.wms.dto.request.LotRequest;
//import com.wms.wms.dto.response.ResponseData;
//import com.wms.wms.dto.response.ResponseSuccess;
//import com.wms.wms.dto.response.lot.LotDetailResponse;
//import com.wms.wms.dto.response.order.MaterialOrderDetailResponse;
//import com.wms.wms.service.LotService;
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
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/lots")
//public class LotController {
//    private final LotService lotService;
//
//    @GetMapping("")
//    public ResponseEntity<ResponseData> findAll() {
//        log.info("Request get lot list");
//        List<LotDetailResponse> response = lotService.findAll();
//        return new ResponseSuccess(HttpStatus.OK, "Get lot list successfully", response);
//    }
//
//    @PostMapping("")
//    public ResponseEntity<ResponseData> addLot(@RequestBody @Valid LotRequest requestDTO) {
//        log.info("Request add lot");
//        LotDetailResponse response = lotService.save(requestDTO);
//        return new ResponseSuccess(HttpStatus.OK, "Create lot successfully",response);
//    }
//
//    @GetMapping("/{lotId}")
//    public ResponseEntity<ResponseData> findById(@Min(value = 0, message = "Id must be greater than 0")
//                                                 @PathVariable("lotId") int lotId) {
//        log.info("Get Material order detail id: {}", lotId);
//        LotDetailResponse response = lotService.findById(lotId);
//        return new ResponseSuccess(HttpStatus.OK, "Get lotId detail successfully", response);
//    }
//
//    @DeleteMapping("/{lotId}")
//    public ResponseEntity<ResponseData> deleteById( @Min(value = 0, message = "Id must be greater than 0")
//                                                    @PathVariable("lotId") int lotId) {
//        log.info("Request delete lot Id={}", lotId);
//        lotService.deleteById(lotId);
//        return new ResponseSuccess(HttpStatus.OK, "Delete lot successfully");
//    }
//
//    @PostMapping("/{lotId}/completed")
//    public ResponseEntity<ResponseData> changeStatusToDelivered(@Min(value = 0, message = "Id must be greater than 0")
//                                                                @PathVariable("lotId") int lotId) {
//        log.info("Request change status lot Id={} to completed", lotId);
//        lotService.changeStatusToCompleted(lotId);
//        return new ResponseSuccess(HttpStatus.OK, "Change status lot to delivered successfully");
//    }
//}
