package com.wms.wms.dto.response.product;

import com.wms.wms.dto.response.BaseEntityResponse;
import com.wms.wms.entity.Product;
import com.wms.wms.entity.Warehouse;
import com.wms.wms.entity.enumentity.InventoryAction;
import com.wms.wms.entity.enumentity.ProcessType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductWarehouseHistoryResponse extends BaseEntityResponse {
    private Product product;
    private Warehouse warehouse;
    private InventoryAction inventoryAction;
    private ProcessType processType;
    private BigDecimal quantity;
    private  String description;
}
