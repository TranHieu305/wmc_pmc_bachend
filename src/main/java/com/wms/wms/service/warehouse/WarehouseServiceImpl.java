package com.wms.wms.service.warehouse;

import com.wms.wms.dto.request.warehouse.WarehouseRequest;
import com.wms.wms.dto.response.warehouse.WarehouseResponse;
import com.wms.wms.entity.Warehouse;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.warehouse.WarehouseRequestMapper;
import com.wms.wms.mapper.warehouse.WarehouseResponseMapper;
import com.wms.wms.repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;

    @Override
    public List<WarehouseResponse> findAll() {
        List<Warehouse> dbWarehouses = warehouseRepository.findAll();
        log.info("Service Get list warehouse successfully");
        return WarehouseResponseMapper.INSTANCE.toDtoList(dbWarehouses);
    }

    @Override
    public WarehouseResponse findById(Long id) {
        Warehouse warehouse = getWarehouseById(id);
        log.info("Service Get warehouse detail id: {} successfully", id);
        return WarehouseResponseMapper.INSTANCE.toDto(warehouse);
    }

    @Transactional
    @Override
    public WarehouseResponse save(WarehouseRequest warehouseRequest) {
        validateRequest(warehouseRequest);

        Warehouse warehouse = WarehouseRequestMapper.INSTANCE.toEntity(warehouseRequest);

        Warehouse dbWarehouse = warehouseRepository.save(warehouse);
        log.info("Service Add warehouse successfully");
        return WarehouseResponseMapper.INSTANCE.toDto(dbWarehouse);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Warehouse warehouse = getWarehouseById(id);
        warehouseRepository.delete(warehouse);
        log.info("Service Delete warehouse by Id: {} successfully", id);
    }

    private Warehouse getWarehouseById(Long warehouseId) {
        return warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id : " + warehouseId));
    }

    private void validateRequest(WarehouseRequest request) {
        if (request.getId() != 0) {
            getWarehouseById(request.getId());
        }
    }
}
