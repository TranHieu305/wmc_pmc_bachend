package com.wms.wms.dto.request.shipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentRequest {
    @NotBlank(message = "Shipment name cannot be blank")
    @Size(min = 1, max = 255, message = "Shipment name must be between 1 and 255 characters")
    private String name;

    private Long vehicleId;

    private Timestamp date;

    private List<Long> approverIds;
    private List<Long> participantIds;

    @JsonProperty("shipmentBatches")
    private List<ShipmentBatchRequest> shipmentBatchRequests;
}
