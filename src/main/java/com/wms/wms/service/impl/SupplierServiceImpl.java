package com.wms.wms.service.impl;

import com.wms.wms.dao.ISupplierDAO;
import com.wms.wms.dto.request.SupplierRequestDTO;
import com.wms.wms.dto.response.SupplierDetailResponse;
import com.wms.wms.entity.Supplier;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.supplier.SupplierRequestMapper;
import com.wms.wms.mapper.supplier.SupplierResponseMapper;
import com.wms.wms.service.ISupplierService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SupplierServiceImpl implements ISupplierService {
    private ISupplierDAO supplierDAO;

    @Autowired
    public SupplierServiceImpl(ISupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
    }

    @Override
    public List<SupplierDetailResponse> findAll() {
        List<Supplier> dbSuppliers= supplierDAO.findAll();
        List<SupplierDetailResponse> supplierDetailResponseList =
                dbSuppliers.stream().map(SupplierResponseMapper.INSTANCE::supplierToResponse).toList();
        log.info("Get supplier list successfully");
        return supplierDetailResponseList;
    }

    @Override
    public SupplierDetailResponse findById(int id) {
        Supplier supplier = getSupplier(id);
        log.info("Get Supplier detail id: {} successfully", id);
        return SupplierResponseMapper.INSTANCE.supplierToResponse(supplier);
    }

    @Override
    @Transactional
    public SupplierDetailResponse save(SupplierRequestDTO requestDTO) {
        if (requestDTO.getId() != 0) {
            Supplier dbSupplier = getSupplier(requestDTO.getId()); // Check if supplier with ID is exist
        }
        Supplier supplier = SupplierRequestMapper.INSTANCE.requestToSupplier(requestDTO);
        Supplier dbSupplier = supplierDAO.save(supplier);
        log.info("Update supplier successfully");
        return SupplierResponseMapper.INSTANCE.supplierToResponse(dbSupplier);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Supplier supplier = getSupplier(id); // Validate id
        supplierDAO.deleteById(id);
        log.info("Delete supplier id: {} successfully", id);
    }

    /**
     * Retrieves the supplier with the given ID from the database.
     * Throws a ResourceNotFoundException if the supplier does not exist.
     *
     * @param id The ID of the supplier to retrieve
     * @return The supplier entity from the database
     * @throws ResourceNotFoundException If the supplier with the given ID does not exist
     */
    private Supplier getSupplier(int id) {
        Supplier dbSupplier = supplierDAO.findById(id);
        if (dbSupplier == null) {
            throw new ResourceNotFoundException("Supplier is not valid");
        }
        return dbSupplier;
    }
}
