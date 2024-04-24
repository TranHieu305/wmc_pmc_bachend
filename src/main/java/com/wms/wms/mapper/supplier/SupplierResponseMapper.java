package com.wms.wms.mapper.supplier;

import com.wms.wms.dto.response.SupplierDetailResponse;
import com.wms.wms.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SupplierResponseMapper {
    SupplierResponseMapper INSTANCE = Mappers.getMapper(SupplierResponseMapper.class);

    SupplierDetailResponse supplierToResponse(Supplier supplier);
}
