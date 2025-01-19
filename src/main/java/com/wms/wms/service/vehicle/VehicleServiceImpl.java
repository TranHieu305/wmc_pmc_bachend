package com.wms.wms.service.vehicle;

import com.wms.wms.dto.request.vehicle.VehicleRequest;
import com.wms.wms.dto.response.vehicle.VehicleResponse;
import com.wms.wms.entity.Vehicle;
import com.wms.wms.entity.enumentity.status.VehicleStatus;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.vehicle.VehicleRequestMapper;
import com.wms.wms.mapper.vehicle.VehicleResponseMapper;
import com.wms.wms.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleServiceImpl implements VehicleService{
    private final VehicleRepository vehicleRepository;

    @Override
    public List<VehicleResponse> findAll() {
        List<Vehicle> dbVehicles = vehicleRepository.findAll();
        log.info("Service Vehicle - Get all successfully");
        return VehicleResponseMapper.INSTANCE.toDtoList(dbVehicles);
    }

    @Override
    public VehicleResponse findById(Long entityId) {
        return null;
    }

    @Override
    @Transactional
    public VehicleResponse save(VehicleRequest vehicleRequest) {
        Vehicle vehicle;

        if (vehicleRequest.getId() != 0) {
            vehicle = getById(vehicleRequest.getId());
        }
        vehicle = VehicleRequestMapper.INSTANCE.toEntity(vehicleRequest);

        Vehicle dbVehicle = vehicleRepository.save(vehicle);
        log.info("Service Vehicle - Save vehicle successfully");
        return VehicleResponseMapper.INSTANCE.toDto(dbVehicle);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Vehicle vehicle = getById(id);
        vehicleRepository.delete(vehicle);
        log.info("Service Vehicle - Delete vehicle by Id: {} successfully", id);
    }

    @Override
    @Transactional
    public void changeStatus(Vehicle vehicle, VehicleStatus status) {
        vehicle.setStatus(status);
        vehicleRepository.save(vehicle);
        log.info("Service Vehicle - update status vehicle by Id: {} to {} successfully", vehicle.getId(), status);
    }

    private Vehicle getById(Long vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id : " + vehicleId));
    }
}
