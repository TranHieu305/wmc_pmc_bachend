package com.wms.wms.service.warehouse;

import com.wms.wms.dao.warehouse.IWarehouseDAO;
import com.wms.wms.entity.Warehouse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService implements  IWarehouseService{
    private IWarehouseDAO iWarehouseDAO;

    public WarehouseService(IWarehouseDAO iWarehouseDAO) {
        this.iWarehouseDAO = iWarehouseDAO;
    }

    @Override
    public List<Warehouse> findAll() {
        return iWarehouseDAO.findAll();
    }

    @Override
    public Warehouse findById(int id) {
        return iWarehouseDAO.findById(id);
    }

    @Transactional
    @Override
    public Warehouse save(Warehouse warehouse) {
        return iWarehouseDAO.save(warehouse);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        iWarehouseDAO.deleteById(id);
    }
}
