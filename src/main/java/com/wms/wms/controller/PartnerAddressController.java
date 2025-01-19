package com.wms.wms.controller;

import com.wms.wms.dto.request.partner.PartnerAddressRequest;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.entity.PartnerAddress;
import com.wms.wms.service.partner.PartnerAddressService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/partner-addresses")
@RequiredArgsConstructor
public class PartnerAddressController {
    private final PartnerAddressService partnerAddressService;
    @GetMapping("")
    public ResponseSuccess findAll() {
        List<PartnerAddress> responses = partnerAddressService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get all PartnerAddress successfully", responses);
    }

    @PostMapping("")
    public ResponseSuccess addAddress(@RequestBody @Valid PartnerAddressRequest request) {
        log.info("Request add partner address");
        request.setId(0L);
        PartnerAddress response = partnerAddressService.save(request);
        return new ResponseSuccess(HttpStatus.CREATED, "Partner added successfully", response);
    }

    @PutMapping("")
    public ResponseSuccess updateAddress(@RequestBody @Valid PartnerAddressRequest request) {
        log.info("Request update partner");
        PartnerAddress response = partnerAddressService.save(request);
        return new ResponseSuccess(HttpStatus.ACCEPTED, "Partner updated successfully", response);
    }

    @DeleteMapping("/{addressId}")
    public ResponseSuccess deleteById(
            @Min(value = 1, message = "Id must be greater than 0") @PathVariable("addressId") Long addressId) {
        log.info("Request delete partner address Id={}", addressId);
        partnerAddressService.deleteById(addressId);
        return new ResponseSuccess(HttpStatus.NO_CONTENT, "Successfully deleted partner address id: " + addressId);
    }
}
