package com.wms.wms.service;

import com.wms.wms.entity.Product;
import com.wms.wms.entity.ProductCategory;
import com.wms.wms.entity.Supplier;

public interface IEntityRetrievalService {
    Supplier getSupplierById(int supplierId);

    Product getProductById(int productId);

    ProductCategory getProductCategoryById(int categoryId);
}
