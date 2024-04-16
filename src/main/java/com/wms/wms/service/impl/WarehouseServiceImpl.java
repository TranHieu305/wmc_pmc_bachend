package com.wms.wms.service.impl;

import com.wms.wms.dao.warehouse.IWarehouseDAO;
import com.wms.wms.entity.Warehouse;
import com.wms.wms.exception.ObjectNotFoundException;
import com.wms.wms.service.IWarehouseService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceImpl implements IWarehouseService {
    private IWarehouseDAO iWarehouseDAO;

    public WarehouseServiceImpl(IWarehouseDAO iWarehouseDAO) {
        this.iWarehouseDAO = iWarehouseDAO;
    }

    @Override
    public List<Warehouse> findAll() {
        return iWarehouseDAO.findAll();
    }

    @Override
    public Warehouse findById(int id) throws ObjectNotFoundException {
        Warehouse warehouse = iWarehouseDAO.findById(id);

        if (warehouse == null) {
            throw new ObjectNotFoundException("Warehouse not found with id : " + id);
        }
        return warehouse;
    }

    @Transactional
    @Override
    public Warehouse save(Warehouse warehouse) {
        return iWarehouseDAO.save(warehouse);
    }

    @Transactional
    @Override
    public void deleteById(int id) throws ObjectNotFoundException{
        Warehouse warehouse = iWarehouseDAO.findById(id);
        if (warehouse == null) {
            throw new ObjectNotFoundException("Warehouse not found with id : " + id);
        }

        iWarehouseDAO.deleteById(id);
    }
}
