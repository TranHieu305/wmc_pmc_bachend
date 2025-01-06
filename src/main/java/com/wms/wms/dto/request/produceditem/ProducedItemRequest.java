package com.wms.wms.dto.request.produceditem;

import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ProducedItemRequest {
    private Long id;
    @Digits(integer = 10, fraction = 6, message = "BatchItemRequest quantity must be decimal")
    private BigDecimal quantity;
    private Long batchItemId;
    private List<Long> approverIds;
    private Set<Long> manufacturerIds;
}
