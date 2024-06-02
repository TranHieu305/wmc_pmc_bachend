package com.wms.wms.dto.response.supplier;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class SupplierDetailResponse {
    private int id;
    private String name;
    private String description;
    private String address;
    private String email;
    private String phone;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
