package com.wms.wms.service.shipment;

import com.wms.wms.dto.request.shipment.ShipmentBatchRequest;
import com.wms.wms.dto.request.shipment.ShipmentRequest;
import com.wms.wms.entity.*;
import com.wms.wms.entity.enumentity.base.UserRole;
import com.wms.wms.entity.enumentity.status.ShipmentStatus;
import com.wms.wms.entity.enumentity.status.VehicleStatus;
import com.wms.wms.exception.ConstraintViolationException;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.repository.BatchRepository;
import com.wms.wms.repository.PartnerAddressRepository;
import com.wms.wms.repository.ShipmentRepository;
import com.wms.wms.repository.VehicleRepository;
import com.wms.wms.service.batch.BatchService;
import com.wms.wms.service.entityfollowing.EntityFollowingService;
import com.wms.wms.service.user.UserService;
import com.wms.wms.service.vehicle.VehicleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private final VehicleService vehicleService;
    private final ShipmentBatchService shipmentBatchService;
    private final BatchService batchService;

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

        // Save following
        Set<Long> followerIds = Stream.concat(dbShipment.getApproverIds().stream(), dbShipment.getParticipantIds().stream())
                .collect(Collectors.toSet());
        entityFollowingService.addFollowingUsersToEntity(followerIds, Shipment.class.getSimpleName(), dbShipment.getId());

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
                            .batch(batch)
                            .partnerId(batch.getPartner().getId())
                            .partnerAddress(partnerAddress)
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
        log.info("Service Shipment - Reject shipment ID {} successfully", shipmentId);
    }

    @Override
    @Transactional
    public void markAsInTransit(Long shipmentId) {
        Shipment shipment = getById(shipmentId);
        if (shipment.getStatus() != ShipmentStatus.PENDING) {
            throw new ConstraintViolationException("Only 'pending' shipments can be marked as in transit");
        }

        if (shipment.getVehicle().getStatus() == VehicleStatus.IN_TRANSIT) {
            throw new ConstraintViolationException("Vehicle is not available");
        }

        shipment.setStatus(ShipmentStatus.IN_TRANSIT);
        shipmentRepository.save(shipment);
        log.info("Service Shipment - Mark as in_transit shipment ID {} successfully", shipmentId);

        // Update status shipment vehicle
        vehicleService.changeStatus(shipment.getVehicle(), VehicleStatus.IN_TRANSIT);

        //Update status of all shipment batches to IN_TRANSIT
        shipmentBatchService.processInTransitShipment(shipment);

        //Update status of all batch and process export
        for (ShipmentBatch shipmentBatch : shipment.getShipmentBatches()) {
            Batch batch = shipmentBatch.getBatch();
            batchService.processInTransit(batch);
        }
    }

    @Override
    @Transactional
    public void markAsCompleted(Long shipmentId) {
        Shipment shipment = getById(shipmentId);
        if (shipment.getStatus() != ShipmentStatus.IN_TRANSIT) {
            throw new ConstraintViolationException("Only 'In Transit' shipments can be marked as completed");
        }

        shipment.setStatus(ShipmentStatus.COMPLETED);
        shipmentRepository.save(shipment);
        log.info("Service Shipment - Mark as completed shipment ID {} successfully", shipmentId);

        // Update status shipment vehicle
        vehicleService.changeStatus(shipment.getVehicle(), VehicleStatus.AVAILABLE);
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

    private Shipment getById(Long shipmentId) {
        return shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id : " + shipmentId));
    }
}
