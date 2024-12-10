package com.wms.wms.dto.response;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public abstract class  BaseEntityResponse {
    private Long id;

    private Long createdBy;

    private Timestamp createdAt;

    private Long modifiedBy;

    private Timestamp modifiedAt;
}
