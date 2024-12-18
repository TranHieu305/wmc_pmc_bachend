package com.wms.wms.service.order;

import com.wms.wms.dto.request.order.OrderItemRequest;
import com.wms.wms.dto.request.order.OrderRequest;
import com.wms.wms.dto.response.order.OrderResponse;
import com.wms.wms.entity.Order;
import com.wms.wms.entity.OrderItem;
import com.wms.wms.entity.Partner;
import com.wms.wms.entity.Product;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.order.OrderRequestMapper;
import com.wms.wms.mapper.order.OrderResponseMapper;
import com.wms.wms.repository.OrderRepository;
import com.wms.wms.repository.PartnerRepository;
import com.wms.wms.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PartnerRepository partnerRepository;

    @Override
    @Transactional
    public List<OrderResponse> findAll() {
        List<Order> dbOrder = orderRepository.findAll();
        log.info("Service Order - Get all orders successfully");
        return OrderResponseMapper.INSTANCE.toDtoList(dbOrder);
    }

    @Override
    @Transactional
    public OrderResponse findById(Long entityId) {
        Order dbOrder = this.getOrderById(entityId);
        log.info("Service Order - Get order ID {} successfully", entityId);
        return OrderResponseMapper.INSTANCE.toDto(dbOrder);
    }

    @Override
    public OrderResponse save(OrderRequest orderRequest) {
       return null;
    }

    @Override
    @Transactional
    public OrderResponse create(OrderRequest orderRequest) {

        // Map request to entity without validating order_items
        Order order = OrderRequestMapper.INSTANCE.toEntity(orderRequest);

        // Get and Set partner
        Partner orderPartner = partnerRepository.findById(orderRequest.getPartnerId())
                .orElseThrow(() -> new ResourceNotFoundException("No Order partner exists with the given Id: " + orderRequest.getPartnerId()));
        order.setPartner(orderPartner);

        // Set OrderItems to Order
        List<OrderItem> orderItemList = this.toOrderItems(orderRequest.getOrderItemRequests());
        for (OrderItem item : orderItemList) {
            order.addOrderItem(item);
        }

        // Save to db
        Order dbOrder = orderRepository.save(order);
        log.info("Service order - Save Order successfully with ID: {}", dbOrder.getId());

        return OrderResponseMapper.INSTANCE.toDto(dbOrder);
    }

    @Override
    public OrderResponse update(OrderRequest request) {
        return null;
    }

    private List<OrderItem> toOrderItems (List<OrderItemRequest> orderItemRequests) {

        // Get product list ID
        Set<Long> productIds = orderItemRequests.stream()
                .map(OrderItemRequest::getProductId)
                .collect(Collectors.toSet());

        // Get products
        log.info("Service Order - Get products to validate request");
        List<Product> products = productRepository.findAllById(productIds);

        // Validate and map to OrderItems list
        return orderItemRequests.stream().map(itemRequest -> {
            // Validate product ID
            Product product = products.stream().filter(p -> p.getId().equals(itemRequest.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Product with ID" + itemRequest.getProductId() + "is not exist"));

            // Map to OrderItem
            return (OrderItem) OrderItem.builder()
                    .product(product)
                    .uom(product.getUom())
                    .quantity(itemRequest.getQuantity())
                    .build();
        }).toList();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Order order = this.getOrderById(id);
        orderRepository.delete(order);
        log.info("Service Order - Delete order ID {} successfully", id);
    }

    private Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Order exists with the given Id: " + id));
    }
}
