package com.wms.wms.dto.request.batch;

import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class BatchItemUpdateRequest {
    private Long id;

    @Digits(integer = 10, fraction = 6, message = "BatchItemRequest quantity must be decimal")
    private BigDecimal quantity;

    private BigDecimal weight;

}
