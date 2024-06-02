package com.wms.wms.service;

public interface AssignedOrderItemService {
    void changeStatusToInTransit(int id);
    void changeStatusToDelivered(int id);
    void changeStatusToReturned(int id);
}
