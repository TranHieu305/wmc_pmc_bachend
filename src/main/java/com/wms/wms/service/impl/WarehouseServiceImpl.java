package com.wms.wms.service.impl;

import com.wms.wms.dao.IWarehouseDAO;
import com.wms.wms.dto.request.WarehouseRequestDTO;
import com.wms.wms.dto.response.WarehouseDetailResponse;
import com.wms.wms.entity.Warehouse;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.warehouse.WarehouseRequestMapper;
import com.wms.wms.mapper.warehouse.WarehouseResponseMapper;
import com.wms.wms.service.IWarehouseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WarehouseServiceImpl implements IWarehouseService {
    private final IWarehouseDAO warehouseDAO;

    @Override
    public List<WarehouseDetailResponse> findAll() {
        log.info("Get list warehouse service");
        List<Warehouse> dbWarehouses = warehouseDAO.findAll();
        return dbWarehouses.stream().map(warehouse -> WarehouseResponseMapper.INSTANCE.warehouseToResponse(warehouse)).toList();
    }

    @Override
    public WarehouseDetailResponse findById(int id) {
        log.info("Get warehouse detail service id: {}", id);
        Warehouse warehouse = getWarehouseById(id);
        return WarehouseResponseMapper.INSTANCE.warehouseToResponse(warehouse);
    }

    @Transactional
    @Override
    public WarehouseDetailResponse save(WarehouseRequestDTO warehouseRequestDTO) {
        log.info("Add warehouse service");
        Warehouse warehouse = WarehouseRequestMapper.INSTANCE.requestToWarehouse(warehouseRequestDTO);
        Warehouse dbWarehouse = warehouseDAO.save(warehouse);
        return WarehouseResponseMapper.INSTANCE.warehouseToResponse(dbWarehouse);
    }

    @Transactional
    @Override
    public WarehouseDetailResponse update(int warehouseId, WarehouseRequestDTO request) {
        log.info("Update warehouse service id: {}", warehouseId);
        Warehouse dbWarehouse = getWarehouseById(warehouseId);
        Warehouse updateWarehouse = WarehouseRequestMapper.INSTANCE.requestToWarehouse(request);
        updateWarehouse.setId(dbWarehouse.getId());

        Warehouse updatedDbWarehouse = warehouseDAO.save(updateWarehouse);
        return WarehouseResponseMapper.INSTANCE.warehouseToResponse(updatedDbWarehouse);
    }

    @Transactional
    @Override
    public void deleteById(int id) throws ResourceNotFoundException {
        log.info("Delete warehouse by Id: {}", id);
        warehouseDAO.deleteById(id);
    }

    private Warehouse getWarehouseById(int id) {
        Warehouse dbWarehouse = warehouseDAO.findById(id);
        if (dbWarehouse == null) {
            throw new ResourceNotFoundException("Warehouse not found with id : " + id);
        }
        return  dbWarehouse;
    }

}
