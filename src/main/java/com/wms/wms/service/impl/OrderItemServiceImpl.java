package com.wms.wms.service.impl;

import com.wms.wms.entity.OrderItem;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.repository.OrderItemRepository;
import com.wms.wms.service.OrderItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderItemServiceImpl implements OrderItemService {
}
