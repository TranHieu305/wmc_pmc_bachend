package com.wms.wms.service;

import com.wms.wms.dto.request.LotRequest;
import com.wms.wms.dto.response.lot.LotDetailResponse;
import com.wms.wms.entity.Lot;

import java.util.List;

public interface LotService {
    LotDetailResponse save(LotRequest request);

    List<LotDetailResponse> findAll();

    void changeStatusToCompleted(int lotId);
}
