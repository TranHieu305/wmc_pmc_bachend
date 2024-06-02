package com.wms.wms.controller;

import com.wms.wms.dto.request.LotRequest;
import com.wms.wms.dto.response.ResponseData;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.dto.response.lot.LotDetailResponse;
import com.wms.wms.service.LotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lots")
public class LotController {
    private final LotService lotService;

    @GetMapping("")
    public ResponseEntity<ResponseData> findAll() {
        log.info("Request get lot list");
        List<LotDetailResponse> response = lotService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get lot list successfully", response);
    }

    @PostMapping("")
    public ResponseEntity<ResponseData> addLot(@RequestBody @Valid LotRequest requestDTO) {
        log.info("Request add lot");
        LotDetailResponse response = lotService.save(requestDTO);
        return new ResponseSuccess(HttpStatus.OK, "Create lot successfully",response);
    }
}
