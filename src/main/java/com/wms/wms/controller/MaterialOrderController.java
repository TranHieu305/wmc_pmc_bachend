package com.wms.wms.controller;


import com.wms.wms.dto.request.MaterialOrderRequestDTO;
import com.wms.wms.dto.response.ResponseData;
import com.wms.wms.service.IMaterialOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:3000", "https://phucthanhwms.netlify.app" })
@RequestMapping("/api")
public class MaterialOrderController {
    private final IMaterialOrderService materialOrderService;

    private static final String ERROR_MESSAGE = "errorMessage={}";

    @PostMapping("/material-orders")
    public ResponseEntity<ResponseData> addMaterialOrder(@RequestBody @Valid MaterialOrderRequestDTO requestDTO) {
        log.info("Request add material order");
        return null;
    }
}
