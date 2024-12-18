package com.wms.wms.dto.request.batch;

import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class BatchItemRequest {
    private Long id;

    @Digits(integer = 10, fraction = 6, message = "BatchItemRequest quantity must be decimal")
    private BigDecimal quantity;
    private Long orderItemId;
    private BigDecimal weight;
}
