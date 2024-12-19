package com.wms.wms.service.order;

import com.wms.wms.dto.request.order.OrderItemRequest;
import com.wms.wms.dto.request.order.OrderRequest;
import com.wms.wms.dto.request.order.OrderUpdateRequest;
import com.wms.wms.dto.response.order.OrderResponse;
import com.wms.wms.entity.*;
import com.wms.wms.exception.ConstraintViolationException;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.order.OrderRequestMapper;
import com.wms.wms.mapper.order.OrderResponseMapper;
import com.wms.wms.repository.BatchRepository;
import com.wms.wms.repository.OrderRepository;
import com.wms.wms.repository.PartnerRepository;
import com.wms.wms.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PartnerRepository partnerRepository;
    private final BatchRepository batchRepository;

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
    public OrderResponse update(OrderUpdateRequest request) {
        Order dbOrder = this.getOrderById(request.getId());
        dbOrder.setName(request.getName());
        dbOrder.setOrderDate(request.getOrderDate());
        dbOrder.setExpectedDeliveryDate(request.getExpectedDeliveryDate());

        Order newOrder = orderRepository.save(dbOrder);
        log.info("Service order - Update Order successfully with ID: {}", dbOrder.getId());
        return OrderResponseMapper.INSTANCE.toDto(newOrder);
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

        List<Batch> batches = batchRepository.findByOrderId(id);
        if (!batches.isEmpty()) {
            throw new ConstraintViolationException("Cannot delete an order that already has a batch. Please delete batch first");
        }
        orderRepository.delete(order);
        log.info("Service Order - Delete order ID {} successfully", id);
    }

    @Override
    @Transactional
    public void addItem(Long orderId, OrderItemRequest itemRequest) {
        Order order = this.getOrderById(orderId);

        Optional<OrderItem> existItem = order.getOrderItems()
                .stream()
                .filter(orderItem -> orderItem.getProduct().getId().equals(itemRequest.getProductId()))
                .findFirst();

        if (existItem.isPresent()) {
            OrderItem item = existItem.get();
            BigDecimal newQuantity = item.getQuantity().add(itemRequest.getQuantity());
            item.setQuantity(newQuantity);
        }
        else {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("No Product exists"));
            OrderItem newItem = OrderItem.builder()
                    .product(product)
                    .uom(product.getUom())
                    .quantity(itemRequest.getQuantity())
                    .build();

            order.addOrderItem(newItem);
        }
        orderRepository.save(order);
        log.info("Service Order - Update order ID {} successfully", orderId);
    }

    private Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Order exists with the given Id: " + id));
    }
}
