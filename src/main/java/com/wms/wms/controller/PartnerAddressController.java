package com.wms.wms.controller;

import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.entity.PartnerAddress;
import com.wms.wms.service.partner.PartnerAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
