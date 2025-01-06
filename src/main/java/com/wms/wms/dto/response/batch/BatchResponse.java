package com.wms.wms.dto.response.batch;

import com.wms.wms.dto.response.BaseEntityResponse;
import com.wms.wms.entity.Order;
import com.wms.wms.entity.Partner;
import com.wms.wms.entity.Warehouse;
import com.wms.wms.entity.enumentity.status.BatchStatus;
import com.wms.wms.entity.enumentity.type.InventoryAction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BatchResponse extends BaseEntityResponse {
    private String name;
    private InventoryAction inventoryAction;
    private InventoryAction orderInventoryAction;
    private BatchStatus status;
    private Timestamp batchDate;
    private Partner partner;
    private Order order;
    private Warehouse warehouse;
    private List<BatchItemResponse> batchItems;
    private Set<Long> approverIds;
    private Set<Long> pendingApproverIds;
    private Set<Long> participantIds;
}
