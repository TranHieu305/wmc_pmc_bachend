package com.wms.wms.service.impl;

import com.wms.wms.dto.request.SupplierRequest;
import com.wms.wms.dto.response.supplier.SupplierDetailResponse;
import com.wms.wms.entity.Supplier;
import com.wms.wms.exception.ConstraintViolationException;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.repository.MaterialOrderRepository;
import com.wms.wms.repository.SupplierRepository;
import com.wms.wms.service.ISupplierService;
import com.wms.wms.util.StringHelper;
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
    private MaterialOrderRepository materialOrderRepository;

    @Override
    public List<SupplierDetailResponse> findAll() {
        List<Supplier> dbSuppliers= supplierRepository.findAll();
        List<SupplierDetailResponse> supplierDetailResponseList =
                dbSuppliers.stream().map(this::convertToDetailResponse).toList();
        log.info("Get supplier list successfully");
        return supplierDetailResponseList;
    }

    @Override
    public SupplierDetailResponse findById(int id) {
        Supplier supplier = this.getSupplierById(id);
        log.info("Get Supplier detail id: {} successfully", id);
        return this.convertToDetailResponse(supplier);
    }

    @Override
    @Transactional
    public SupplierDetailResponse save(SupplierRequest requestDTO) {
        Supplier supplier;
        if (requestDTO.getId() != 0) {
            supplier = this.getSupplierById(requestDTO.getId());
        }
        else  {
            supplier = Supplier.builder().build();
        }
        supplier.setName(StringHelper.preProcess(requestDTO.getName()));
        supplier.setDescription(requestDTO.getDescription());
        supplier.setAddress(requestDTO.getAddress());
        supplier.setEmail(requestDTO.getEmail());
        supplier.setPhone(requestDTO.getPhone());

        Supplier dbSupplier = supplierRepository.save(supplier);
        log.info("Update supplier successfully");
        return this.convertToDetailResponse(dbSupplier);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Supplier supplier = this.getSupplierById(id); // Validate id
        this.checkConstraintToDelete(supplier);
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

    private void checkConstraintToDelete(Supplier supplier) {
        if (materialOrderRepository.existsBySupplierId(supplier.getId())) {
            throw new ConstraintViolationException("Cannot delete supplier. Associated orders exist.");
        }
    }

    private SupplierDetailResponse convertToDetailResponse (Supplier supplier) {
        return SupplierDetailResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .description(supplier.getDescription())
                .address(supplier.getAddress())
                .email(supplier.getEmail())
                .phone(supplier.getPhone())
                .createdAt(supplier.getCreatedAt())
                .modifiedAt(supplier.getModifiedAt())
                .build();
    }
}
