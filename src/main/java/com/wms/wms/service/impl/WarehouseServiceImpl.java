package com.wms.wms.service.impl;

import com.wms.wms.dto.request.WarehouseRequestDTO;
import com.wms.wms.dto.response.warehouse.WarehouseDetailResponse;
import com.wms.wms.entity.Warehouse;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.repository.WarehouseRepository;
import com.wms.wms.service.IWarehouseService;
import com.wms.wms.util.StringHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WarehouseServiceImpl implements IWarehouseService {
    private final WarehouseRepository warehouseRepository;

    @Override
    public List<WarehouseDetailResponse> findAll() {
        List<Warehouse> dbWarehouses = warehouseRepository.findAll();
        log.info("Get list warehouse successfully");
        return dbWarehouses.stream().map(this::convertToDetailResponse).toList();
    }

    @Override
    public WarehouseDetailResponse findById(int id) {
        Warehouse warehouse = getWarehouseById(id);
        log.info("Get warehouse detail service id: {} successfully", id);
        return this.convertToDetailResponse(warehouse);
    }

    @Transactional
    @Override
    public WarehouseDetailResponse save(WarehouseRequestDTO warehouseRequestDTO) {
        Warehouse warehouse;
        if (warehouseRequestDTO.getId() != 0) {
            warehouse = this.getWarehouseById(warehouseRequestDTO.getId());
        }
        else {
            warehouse = Warehouse.builder().build();
        }
        warehouse.setName(StringHelper.preProcess(warehouseRequestDTO.getName()));
        warehouse.setDescription(warehouseRequestDTO.getDescription());

        Warehouse dbWarehouse = warehouseRepository.save(warehouse);
        log.info("Add warehouse service successfully");
        return this.convertToDetailResponse(dbWarehouse);
    }

    @Transactional
    @Override
    public void deleteById(int id) throws ResourceNotFoundException {
        Warehouse warehouse = getWarehouseById(id);
        warehouseRepository.delete(warehouse);
        log.info("Delete warehouse by Id: {} successfully", id);
    }

    @Override
    public Warehouse getWarehouseById(int warehouseId) {
        return warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id : " + warehouseId));
    }

    private WarehouseDetailResponse convertToDetailResponse(Warehouse warehouse) {
        return WarehouseDetailResponse.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .description(warehouse.getDescription())
                .address(warehouse.getAddress())
                .createdAt(warehouse.getCreatedAt())
                .modifiedAt(warehouse.getModifiedAt())
                .build();
    }
}
