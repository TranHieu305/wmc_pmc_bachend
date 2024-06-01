package com.wms.wms.service;

import com.wms.wms.dto.request.LotRequest;
import com.wms.wms.dto.response.lot.LotDetailResponse;

import java.util.List;

public interface ILotService {
    LotDetailResponse save(LotRequest request);

    List<LotDetailResponse> findAll();
}
