package com.jfood.jFood.repository;

import com.jfood.jFood.entity.Order;
import com.jfood.jFood.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByUser(User user);

    List<Order> findByCourier(User courier);
}

