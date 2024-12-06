package com.wms.wms.controller;

import com.wms.wms.dto.request.partner.PartnerContactRequest;
import com.wms.wms.dto.response.ResponseSuccess;
import com.wms.wms.dto.response.partner.PartnerContactResponse;
import com.wms.wms.service.partner.PartnerContactService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partner-contacts")
@RequiredArgsConstructor
public class PartnerContactController {
    private final PartnerContactService partnerContactService;

    @GetMapping("")
    public ResponseSuccess findAll() {
        List<PartnerContactResponse> responses = partnerContactService.findAll();
        return new ResponseSuccess(HttpStatus.OK, "Get all partnerContacts successfully", responses);
    }

    @PostMapping("")
    public ResponseSuccess addPartnerContact(@RequestBody @Valid PartnerContactRequest request) {
        request.setId(0L);
        PartnerContactResponse response = partnerContactService.save(request);

        return new ResponseSuccess(HttpStatus.CREATED, "PartnerContact added successfully", response);
    }

    @PutMapping("")
    public ResponseSuccess updatePartnerContact(@RequestBody @Valid PartnerContactRequest request) {
        PartnerContactResponse response = partnerContactService.save(request);
        return new ResponseSuccess(HttpStatus.ACCEPTED, "PartnerContact updated successfully", response);
    }

    // Get warehouse by id
    @GetMapping("/{partnerContactId}")
    public ResponseSuccess findById(
            @Min(value = 1, message = "Id must be greater than 0") @PathVariable Long partnerContactId) {

        PartnerContactResponse response = partnerContactService.findById(partnerContactId);
        return new ResponseSuccess(HttpStatus.OK, "Get the partnerContact id " + partnerContactId + " successfully", response);
    }

    @DeleteMapping("/{partnerContactId}")
    public ResponseSuccess deleteById(
            @Min(value = 1, message = "Id must be greater than 0") @PathVariable Long partnerContactId) {

        partnerContactService.deleteById(partnerContactId);
        return new ResponseSuccess(HttpStatus.NO_CONTENT, "Successfully deleted partnerContact id: " + partnerContactId);
    }

}
