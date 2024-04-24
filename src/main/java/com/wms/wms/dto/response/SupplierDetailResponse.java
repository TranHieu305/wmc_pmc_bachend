package com.wms.wms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDetailResponse {
    private int id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
