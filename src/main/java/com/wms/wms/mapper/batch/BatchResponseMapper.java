package com.wms.wms.mapper.batch;

import com.wms.wms.dto.response.batch.BatchResponse;
import com.wms.wms.entity.Batch;
import com.wms.wms.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BatchResponseMapper extends BaseMapper<BatchResponse, Batch> {
    BatchResponseMapper INSTANCE = Mappers.getMapper(BatchResponseMapper.class);
}
