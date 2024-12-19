package com.wms.wms.service.order;

import com.wms.wms.dto.request.order.OrderItemUpdateRequest;
import com.wms.wms.entity.BatchItem;
import com.wms.wms.entity.OrderItem;
import com.wms.wms.exception.ConstraintViolationException;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.repository.BatchItemRepository;
import com.wms.wms.repository.OrderItemRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final BatchItemRepository batchItemRepository;

    @Override
    @Transactional
    public void updateItem(OrderItemUpdateRequest request) {
        OrderItem item = this.getItemById(request.getId());
        item.setQuantity(request.getQuantity());
        orderItemRepository.save(item);
        log.info("Service Order - Get order item ID {} successfully", item.getId());

    }

    @Override
    @Transactional
    public void deleteItem(Long itemId) {
        OrderItem item = this.getItemById(itemId);
        List<BatchItem> batchItemList = batchItemRepository.findByOrderItemId(itemId);
        if (!batchItemList.isEmpty()) {
            throw new ConstraintViolationException("Cannot delete an order item that already has a batch item. Please delete batch item first");
        }
        orderItemRepository.delete(item);
        log.info("Delete Order - Get order item ID {} successfully", item.getId());
    }

    private OrderItem getItemById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Order item exists with the given Id: " + id));
    }
}
