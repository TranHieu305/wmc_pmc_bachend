package com.wms.wms.service;

import com.wms.wms.entity.*;

import java.util.List;
import java.util.Set;

public interface EntityRetrievalService {
    /*
    ------------Warehouse---------------
   */
    Warehouse getWarehouseById(int warehouseId);

    /*
    ------------Partner---------------
    */
    AbstractPartner getPartnerById(int partnerId);
    Supplier getSupplierById(int supplierId);

    /*
    ------------Product---------------
    */
    Product getProductById(int productId);

    /*
    ------------Product Category---------------
    */
    ProductCategory getProductCategoryById(int categoryId);

    /*
    ------------ProductWarehouse---------------
    */
    ProductWarehouse getProductWarehouseById(int id);
    List<ProductWarehouse> getAllProductOfWarehouse(int warehouseId);
    List<ProductWarehouse> findByWarehouseIdAndProductIdIn(int warehouseId, Set<Integer> productIds);


    /*
     ------------Lot---------------
    */
    Lot getLotById(int lotId);

     /*
     ------------Order---------------
    */
    AbstractOrder getOrderById(int orderId);

    /*
    ------------Order Item---------------
    */
    OrderItem getOrderItemById(int orderItemId);
    List<OrderItem> getOrderItemByIds(Set<Integer> ids);

    /*
    ------------Assigned Order Item---------------
    */
    List<AssignedOrderItem> getAssignedOrderItemByOrderItemId(int orderItemId);
    AssignedOrderItem getAssignedOrderItemById(int itemId);
}
