package com.wms.wms.service.order;

import com.wms.wms.dto.request.order.OrderItemRequest;
import com.wms.wms.dto.request.order.OrderRequest;
import com.wms.wms.dto.request.order.OrderUpdateRequest;
import com.wms.wms.dto.response.order.OrderResponse;
import com.wms.wms.entity.*;
import com.wms.wms.entity.enumentity.base.UserRole;
import com.wms.wms.entity.enumentity.status.OrderStatus;
import com.wms.wms.exception.ConstraintViolationException;
import com.wms.wms.exception.ResourceNotFoundException;
import com.wms.wms.mapper.order.OrderRequestMapper;
import com.wms.wms.mapper.order.OrderResponseMapper;
import com.wms.wms.repository.*;
import com.wms.wms.service.entityfollowing.EntityFollowingService;
import com.wms.wms.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PartnerRepository partnerRepository;
    private final BatchRepository batchRepository;
    private final UserService userService;
    private final EntityFollowingService entityFollowingService;

    @Override
    @Transactional
    public List<OrderResponse> findAll() {
        User currentUser = userService.getCurrentUser();
        List<Order> dbOrder;
        if (currentUser.getRole() == UserRole.ADMIN) {
            dbOrder = orderRepository.findAllByOrderByCreatedAtDesc();
            log.info("Service Order - Get all orders successfully");
        } else {
            List<EntityFollowing> followings = entityFollowingService.getFollowings(currentUser.getId(), Order.class.getSimpleName());
            List<Long> orderIds = followings.stream()
                    .map(EntityFollowing::getEntityId)
                    .toList();
            dbOrder = orderRepository.findAllByIdInOrderByCreatedAtDesc(orderIds);
            log.info("Service Order - Get all following orders successfully");
        }

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

        // Save to db to get ID for order_item
        order = orderRepository.save(order);

        // Set OrderItems to Order
        List<OrderItem> orderItemList = this.toOrderItems(orderRequest.getOrderItemRequests());
        for (OrderItem item : orderItemList) {
            item.setOrderId(order.getId());
            order.addOrderItem(item);
        }

        //Set approvers
        Set<Long> approverIds = new HashSet<>(orderRequest.getApproverIds());
        order.setApproverIds(approverIds);
        order.setPendingApproverIds(approverIds);

        //Set participants
        Set<Long> participantIds = new HashSet<>(orderRequest.getParticipantIds());
        User curentUser = userService.getCurrentUser();
        participantIds.add(curentUser.getId());
        order.setParticipantIds(participantIds);

        // Save to db
        Order dbOrder = orderRepository.save(order);
        log.info("Service order - Save Order successfully with ID: {}", dbOrder.getId());

        // Save following
        Set<Long> followerIds = Stream.concat(dbOrder.getApproverIds().stream(), dbOrder.getParticipantIds().stream())
                .collect(Collectors.toSet());
        entityFollowingService.addFollowingUsersToEntity(followerIds, Order.class.getSimpleName(), dbOrder.getId());

        return OrderResponseMapper.INSTANCE.toDto(dbOrder);
    }

    @Override
    public OrderResponse update(OrderUpdateRequest request) {
        Order dbOrder = this.getOrderById(request.getId());
        if (dbOrder.getStatus() == OrderStatus.COMPLETED) {
            throw new ConstraintViolationException("Cannot update completed order");
        }

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

        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new ConstraintViolationException("Cannot add item to completed order");
        }

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
                    .orderId(order.getId())
                    .build();

            order.addOrderItem(newItem);
        }
        orderRepository.save(order);
        log.info("Service Order - Update order ID {} successfully", orderId);
    }

    @Override
    @Transactional
    public void markAsCompleted(Long orderId) {
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
        log.info("Service Order - Mark status order ID {} as COMPLETED successfully", orderId);
    }

    @Override
    @Transactional
    public void approve(Long orderId) {
        Order order = getOrderById(orderId);
        // Check constraint
        User currentUser = userService.getCurrentUser();
        checkConstraintToApprove(order, currentUser);

        // Process approve
        // Remove from pending approver
        order.getPendingApproverIds().remove(currentUser.getId());
        // If all approvers approved, change status
        if (order.getPendingApproverIds().isEmpty() &&
                order.getStatus() == OrderStatus.PENDING_APPROVAL) {
            order.setStatus(OrderStatus.PENDING);
        }

        orderRepository.save(order);
        log.info("Service Order - Approve order ID {} successfully", orderId);
    }

    @Override
    @Transactional
    public void reject(Long orderId) {
        Order order = getOrderById(orderId);
        // Check constraint
        User currentUser = userService.getCurrentUser();
        checkConstraintToApprove(order, currentUser);

        // Process approve
        // Remove from pending approver
        order.getPendingApproverIds().remove(currentUser.getId());
        order.setStatus(OrderStatus.REJECTED);
        orderRepository.save(order);
        log.info("Service Order - Reject order ID {} successfully", orderId);
    }

    private void checkConstraintToApprove(Order order, User currentUser) {
        if (order.getStatus() != OrderStatus.PENDING_APPROVAL) {
            throw new ConstraintViolationException("Cannot approve order does not have pending approval status");
        }
        if (!order.getApproverIds().contains(currentUser.getId())) {
            throw new ConstraintViolationException("You are not approver");
        }
        if (!order.getPendingApproverIds().contains(currentUser.getId())) {
            throw new ConstraintViolationException("You had already approved");
        }
    }

    private Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Order exists with the given Id: " + id));
    }
}
