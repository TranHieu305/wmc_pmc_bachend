package com.wms.wms.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialOrderRequest {
    private int id;
    private int supplierId;

    @NotBlank(message = "Material order name cannot be blank")
    @Size(min = 1, max = 255, message = "Material order name must be between 1 and 255 characters")
    private String name;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date orderDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date expectedDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date actualDate;

    private String additionalData;

    private String status;

    private List<OrderItemRequest> orderItems;
}
