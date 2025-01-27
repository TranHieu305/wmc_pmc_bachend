package com.wms.wms.dto.response.batch;

import com.wms.wms.dto.response.BaseLogisticItemResponse;
import com.wms.wms.entity.enumentity.status.ItemStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class BatchItemResponse extends BaseLogisticItemResponse {
    private Long orderItemId;
    private BigDecimal weight;
    private Long batchId;
    private ItemStatus status;
    private BigDecimal producedQuantity;
}
