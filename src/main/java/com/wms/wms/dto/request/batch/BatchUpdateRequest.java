package com.wms.wms.dto.request.batch;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class BatchUpdateRequest {
    private Long id;

    @NotBlank(message = "Order name cannot be blank")
    @Size(min = 1, max = 255, message = "Order name must be between 1 and 255 characters")
    private String name;

    private Timestamp batchDate;

}
