package com.wms.wms.service;

import com.wms.wms.entity.*;

import java.util.List;

public interface IEntityRetrievalService {
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

    /*
    ------------Assigned Order Item---------------
    */
    List<AssignedOrderItem> getAssignedOrderItemByOrderItemId(int orderItemId);
    AssignedOrderItem getAssignedOrderItemById(int itemId);
}
