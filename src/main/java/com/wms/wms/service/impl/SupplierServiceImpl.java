package com.wms.wms.service.impl;

import com.wms.wms.dao.ISupplierDAO;
import com.wms.wms.entity.Supplier;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.service.ISupplierService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements ISupplierService {
    private ISupplierDAO iSupplierDAO;

    public SupplierServiceImpl(ISupplierDAO iSupplierDAO) {
        this.iSupplierDAO = iSupplierDAO;
    }

    @Override
    public List<Supplier> findAll() {
        return iSupplierDAO.findAll();
    }

    @Override
    public Supplier findById(int id) throws ResourceNotFoundException {
        Supplier supplier = iSupplierDAO.findById(id);

        if (supplier == null) {
            throw new ResourceNotFoundException("Supplier not found with id : " + id);
        }
        return supplier;
    }

    @Override
    @Transactional
    public Supplier save(Supplier supplier) {
        return iSupplierDAO.save(supplier);
    }

    @Override
    @Transactional
    public void deleteById(int id) throws ResourceNotFoundException {
        Supplier supplier = iSupplierDAO.findById(id);

        if (supplier == null) {
            throw new ResourceNotFoundException("Supplier not found with id : " + id);
        }

        iSupplierDAO.deleteById(id);
    }
}
