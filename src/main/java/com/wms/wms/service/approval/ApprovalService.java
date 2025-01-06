package com.wms.wms.service.approval;

import java.util.List;

public interface ApprovalService {
    void saveApprovals(Long entityId, String entityType, List<Long> approverIds);
}
