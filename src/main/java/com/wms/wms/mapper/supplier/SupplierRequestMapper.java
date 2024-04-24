package com.wms.wms.mapper.supplier;

import com.wms.wms.dto.request.SupplierRequestDTO;
import com.wms.wms.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SupplierRequestMapper {
    SupplierRequestMapper INSTANCE = Mappers.getMapper(SupplierRequestMapper.class);

    Supplier requestToSupplier(SupplierRequestDTO supplierRequestDTO);
}
