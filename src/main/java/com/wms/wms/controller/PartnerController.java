package com.wms.wms.controller;

import com.wms.wms.dto.request.partner.PartnerRequest;
import com.wms.wms.dto.request.partner.PartnerUpdateRequest;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.dto.response.partner.PartnerResponse;
import com.wms.wms.service.partner.PartnerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class PartnerController {
    private final PartnerService partnerService;

    @GetMapping("")
    public ResponseSuccess findAll() {
        log.info("Request get partner list");
        List<PartnerResponse> partnerList = partnerService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get all partners successfully", partnerList);
    }

    @PostMapping("")
    public ResponseSuccess addPartner(@RequestBody @Valid PartnerRequest request) {
        log.info("Request add partner");
        request.setId(0L);
        PartnerResponse response = partnerService.save(request);
        return new ResponseSuccess(HttpStatus.CREATED, "Partner added successfully", response);
    }

    @PutMapping("")
    public ResponseSuccess updatePartner(@RequestBody @Valid PartnerUpdateRequest request) {
        log.info("Request update partner");
        PartnerResponse response = partnerService.update(request);
        return new ResponseSuccess(HttpStatus.ACCEPTED, "Partner updated successfully", response);
    }

    // Get warehouse by id
    @GetMapping("/{partnerId}")
    public ResponseSuccess findById(
            @Min(value = 1, message = "Id must be greater than 0") @PathVariable("partnerId") Long partnerId) {
        log.info("Get partner detail id: {}", partnerId);
        PartnerResponse response = partnerService.findById(partnerId);
        return new ResponseSuccess(HttpStatus.OK, "Get the partner id " + partnerId + " successfully", response);
    }

    @DeleteMapping("/{partnerId}")
    public ResponseSuccess deleteById(
            @Min(value = 1, message = "Id must be greater than 0") @PathVariable("partnerId") Long partnerId) {
        log.info("Request delete partner Id={}", partnerId);
        partnerService.deleteById(partnerId);
        return new ResponseSuccess(HttpStatus.NO_CONTENT, "Successfully deleted partner id: " + partnerId);
    }
}
