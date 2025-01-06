package com.wms.wms.service.approval;

import com.wms.wms.dto.response.User.UserGeneral;
import com.wms.wms.entity.Approval;
import com.wms.wms.entity.enumentity.status.UserStatus;
import com.wms.wms.exception.ConstraintViolationException;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.repository.ApprovalRepository;
import com.wms.wms.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ApprovalServiceImpl implements ApprovalService{
    private final UserService userService;
    private final ApprovalRepository approvalRepository;

    @Override
    @Transactional
    public void saveApprovals(Long entityId, String entityType, List<Long> approverIds) {
        List<UserGeneral> users = userService.findAllGeneral();
        List<Approval> approvals = new ArrayList<>();

        approverIds.forEach(approverId -> {
            UserGeneral approver = validateApprover(users, approverId);
            approvals.add(Approval.builder()
                    .entityId(entityId)
                    .entityName(entityType) // Generalized to accept any entity
                    .approverId(approver.getId())
                    .build());
        });

        approvalRepository.saveAll(approvals);
        log.info("Approval service - save successfully entity name {}, id {}", entityType, entityId);
    }

    private UserGeneral validateApprover(List<UserGeneral> users, Long approverId) {
        Optional<UserGeneral> optionalApprover = users.stream()
                .filter(user -> user.getId().equals(approverId))
                .findFirst();
        if (optionalApprover.isEmpty()) {
            throw new ResourceNotFoundException("User not exist");
        }

        UserGeneral approver = optionalApprover.get();
        if (approver.getStatus() == UserStatus.DISABLE) {
            throw new ConstraintViolationException("User is disabled");
        }

        return approver;
    }
}
