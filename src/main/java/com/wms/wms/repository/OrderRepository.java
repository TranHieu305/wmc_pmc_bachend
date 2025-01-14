package com.wms.wms.repository;

import com.wms.wms.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderByCreatedAtDesc();

    List<Order> findAllByIdInOrderByCreatedAtDesc(List<Long> ids);
}
