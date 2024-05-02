package com.wms.wms.dao;

import com.wms.wms.entity.MaterialOrder;

import java.util.List;

public interface IMaterialOrderDAO {

    List<MaterialOrder> findAll();

    MaterialOrder findById(int id);

    MaterialOrder save(MaterialOrder materialOrder);

    void delete(MaterialOrder materialOrder);
}
