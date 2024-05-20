package com.wms.wms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductGeneralResponse {
    private int id;
    private String name;
    private String description;
    private String categoryName;
}
