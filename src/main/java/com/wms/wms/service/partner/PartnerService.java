package com.wms.wms.service.partner;

import com.wms.wms.dto.request.partner.PartnerRequest;
import com.wms.wms.dto.request.partner.PartnerUpdateRequest;
import com.wms.wms.dto.response.partner.PartnerResponse;
import com.wms.wms.service.BaseService;

public interface PartnerService extends BaseService<PartnerRequest, PartnerResponse> {
    PartnerResponse update(PartnerUpdateRequest request);
}
