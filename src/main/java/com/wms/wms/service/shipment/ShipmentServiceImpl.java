package com.wms.wms.service.shipment;

import com.wms.wms.dto.request.shipment.ShipmentBatchRequest;
import com.wms.wms.dto.request.shipment.ShipmentRequest;
import com.wms.wms.entity.*;
import com.wms.wms.entity.enumentity.base.UserRole;
import com.wms.wms.entity.enumentity.status.ShipmentStatus;
import com.wms.wms.exception.ConstraintViolationException;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.repository.BatchRepository;
import com.wms.wms.repository.PartnerAddressRepository;
import com.wms.wms.repository.ShipmentRepository;
import com.wms.wms.repository.VehicleRepository;
import com.wms.wms.service.entityfollowing.EntityFollowingService;
import com.wms.wms.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ShipmentServiceImpl implements ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final VehicleRepository vehicleRepository;
    private final UserService userService;
    private final BatchRepository batchRepository;
    private final PartnerAddressRepository partnerAddressRepository;
    private final EntityFollowingService entityFollowingService;

    @Override
    public List<Shipment> findAll() {
        List<Shipment> shipments;
        User currentUser = userService.getCurrentUser();
        if (currentUser.getRole() == UserRole.ADMIN) {
            shipments = shipmentRepository.findAllByOrderByCreatedAtDesc();
            log.info("Service Shipment - Get all shipments successfully");
        } else {
            List<EntityFollowing> followings = entityFollowingService.getFollowings(currentUser.getId(), Shipment.class.getSimpleName());
            List<Long> shipmentIds = followings.stream()
                    .map(EntityFollowing::getEntityId)
                    .toList();
            shipments = shipmentRepository.findAllByIdInOrderByCreatedAtDesc(shipmentIds);
            log.info("Service Shipment - Get all following shipments successfully");
        }
        return shipments;
    }

    @Override
    @Transactional
    public Shipment findById(Long entityId) {
        Shipment shipment = getById(entityId);
        log.info("Service Shipment - Get shipment ID {} successfully", entityId);
        return shipment;
    }

    @Override
    @Transactional
    public Shipment save(ShipmentRequest shipmentRequest) {
        Shipment shipment = new Shipment();
        shipment.setName(shipmentRequest.getName());
        shipment.setDate(shipmentRequest.getDate());

        Vehicle vehicle = vehicleRepository.findById(shipmentRequest.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("No vehicle exists with the given Id: " +
                        shipmentRequest.getVehicleId()));
        shipment.setVehicle(vehicle);

        //Set approvers
        Set<Long> approverIds = new HashSet<>(shipmentRequest.getApproverIds());
        shipment.setApproverIds(approverIds);
        shipment.setPendingApproverIds(approverIds);

        //Set participants
        Set<Long> participantIds = new HashSet<>(shipmentRequest.getParticipantIds());
        User curentUser = userService.getCurrentUser();
        participantIds.add(curentUser.getId());
        shipment.setParticipantIds(participantIds);

        // Set ShipmentBatches
        List<ShipmentBatch> shipmentBatches = toShipmentBatches(shipmentRequest.getShipmentBatchRequests());
        for (ShipmentBatch batch : shipmentBatches) {
            shipment.addItem(batch);
        }

        Shipment dbShipment = shipmentRepository.save(shipment);
        log.info("Service Shipment - create shipment ID {} successfully", dbShipment.getId());
        return dbShipment;
    }

    private List<ShipmentBatch> toShipmentBatches (List<ShipmentBatchRequest> shipmentBatchRequests) {
        return shipmentBatchRequests.stream().map(
                shipmentBatchRequest -> {
                    Long batchId = shipmentBatchRequest.getBatchId();
                    Batch batch = batchRepository.findById(batchId)
                            .orElseThrow(() -> new ResourceNotFoundException("No batch exists with the given Id: " + batchId));

                    Long partnerAddressId = shipmentBatchRequest.getPartnerAddressId();
                    PartnerAddress partnerAddress = partnerAddressRepository.findById(partnerAddressId)
                            .orElseThrow(() -> new ResourceNotFoundException("No address exists with the given Id: " + partnerAddressId));

                    return (ShipmentBatch) ShipmentBatch.builder()
                            .batchId(batchId)
                            .partnerId(batch.getPartner().getId())
                            .partnerAddressId(partnerAddress.getId())
                            .shipmentOrder(shipmentBatchRequest.getShipmentOrder())
                            .build();
                }
        ).toList();
    }


    @Override
    @Transactional
    public void deleteById(Long id) {
        Shipment shipment = this.getById(id);
        if (shipment.getStatus() != ShipmentStatus.PENDING_APPROVAL) {
            throw new ConstraintViolationException("Cannot delete approved or rejected shipment");
        }
        shipmentRepository.delete(shipment);
        log.info("Service Shipment - delete shipment ID {} successfully", id);
    }

    private Shipment getById(Long shipmentId) {
        return shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id : " + shipmentId));
    }

    @Override
    @Transactional
    public void approve(Long shipmentId) {
        Shipment shipment = getById(shipmentId);
        // Check constraint
        User currentUser = userService.getCurrentUser();
        checkConstraintToApprove(shipment, currentUser);

        // Process approve
        // Remove from pending approver
        shipment.getPendingApproverIds().remove(currentUser.getId());
        // If all approvers approved, change status
        if (shipment.getPendingApproverIds().isEmpty() &&
                shipment.getStatus() == ShipmentStatus.PENDING_APPROVAL) {
            shipment.setStatus(ShipmentStatus.PENDING);
        }

        shipmentRepository.save(shipment);
        log.info("Service Shipment - Approve shipment ID {} successfully", shipmentId);
    }

    @Override
    @Transactional
    public void reject(Long shipmentId) {
        Shipment shipment = getById(shipmentId);
        // Check constraint
        User currentUser = userService.getCurrentUser();
        checkConstraintToApprove(shipment, currentUser);

        // Process approve
        // Remove from pending approver
        shipment.getPendingApproverIds().remove(currentUser.getId());
        shipment.setStatus(ShipmentStatus.REJECTED);
        shipmentRepository.save(shipment);
        log.info("Service Batch - Reject batch ID {} successfully", shipmentId);
    }

    private void checkConstraintToApprove(Shipment shipment, User currentUser) {
        if (shipment.getStatus() != ShipmentStatus.PENDING_APPROVAL) {
            throw new ConstraintViolationException("Cannot approve shipment does not have pending approval status");
        }
        if (!shipment.getApproverIds().contains(currentUser.getId())) {
            throw new ConstraintViolationException("You are not approver");
        }
        if (!shipment.getPendingApproverIds().contains(currentUser.getId())) {
            throw new ConstraintViolationException("You had already approved");
        }
    }
}
