package com.jfood.jFood.order.repository;

import com.jfood.jFood.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByClientId(Long clientId, Pageable pageable);

    List<Order> findByCourierId(Long courierId);
}
