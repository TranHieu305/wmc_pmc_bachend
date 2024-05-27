package com.wms.wms.service.impl;

import com.wms.wms.dto.request.SupplierRequestDTO;
import com.wms.wms.dto.response.SupplierDetailResponse;
import com.wms.wms.entity.Supplier;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.supplier.SupplierRequestMapper;
import com.wms.wms.mapper.supplier.SupplierResponseMapper;
import com.wms.wms.repository.SupplierRepository;
import com.wms.wms.service.ISupplierService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SupplierServiceImpl implements ISupplierService {
    private SupplierRepository supplierRepository;

    @Override
    public List<SupplierDetailResponse> findAll() {
        List<Supplier> dbSuppliers= supplierRepository.findAll();
        List<SupplierDetailResponse> supplierDetailResponseList =
                dbSuppliers.stream().map(SupplierResponseMapper.INSTANCE::supplierToResponse).toList();
        log.info("Get supplier list successfully");
        return supplierDetailResponseList;
    }

    @Override
    public SupplierDetailResponse findById(int id) {
        Supplier supplier = this.getSupplierById(id);
        log.info("Get Supplier detail id: {} successfully", id);
        return SupplierResponseMapper.INSTANCE.supplierToResponse(supplier);
    }

    @Override
    @Transactional
    public SupplierDetailResponse save(SupplierRequestDTO requestDTO) {
        // Validate supplier ID
        if (requestDTO.getId() != 0) {
            Supplier dbSupplier = this.getSupplierById(requestDTO.getId());
        }
        Supplier supplier = SupplierRequestMapper.INSTANCE.requestToSupplier(requestDTO);
        Supplier dbSupplier = supplierRepository.save(supplier);
        log.info("Update supplier successfully");
        return SupplierResponseMapper.INSTANCE.supplierToResponse(dbSupplier);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Supplier supplier = this.getSupplierById(id); // Validate id
        supplierRepository.delete(supplier);
        log.info("Delete supplier id: {} successfully", id);
    }

    /**
     * Retrieves the supplier with the given ID from the database.
     *
     * @param supplierId The ID of the supplier to retrieve
     * @return The supplier entity from the database
     * @throws ResourceNotFoundException If the supplier with the given ID does not exist
     */
    public Supplier getSupplierById(int supplierId) {
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("No supplier exists with the given Id: " + supplierId));
    }
}
