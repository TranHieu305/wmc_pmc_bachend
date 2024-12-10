package com.wms.wms.dto.response.product;

import com.wms.wms.dto.response.BaseEntityResponse;
import com.wms.wms.entity.enumentity.CategoryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ProductCategoryResponse extends BaseEntityResponse {
    private String name;
    private String description;
    private CategoryType categoryType;
}
