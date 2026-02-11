package com.jfood.jFood.service.impl;

import com.jfood.jFood.dto.request.OrderRequest;
import com.jfood.jFood.dto.request.UpdateStatusRequest;
import com.jfood.jFood.dto.response.DishResponse;
import com.jfood.jFood.dto.response.OrderResponse;
import com.jfood.jFood.dto.response.UserResponse;
import com.jfood.jFood.entity.Dish;
import com.jfood.jFood.entity.Order;
import com.jfood.jFood.entity.OrderStatus;
import com.jfood.jFood.entity.User;
import com.jfood.jFood.exception.NotFoundException;
import com.jfood.jFood.repository.DishRepository;
import com.jfood.jFood.repository.OrderRepository;
import com.jfood.jFood.repository.UserRepository;
import com.jfood.jFood.service.OrderService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            DishRepository dishRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
    }

    @Override
    public List<OrderResponse> getUserOrders(UserDetails userDetails) {
        User user = getUserByLogin(userDetails.getUsername());
        return orderRepository.findByUser(user).stream()
                .map(this::toOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse getOrder(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заказ не найден"));
        return toOrderResponse(order);
    }

    @Override
    public OrderResponse createOrder(OrderRequest request, UserDetails userDetails) {
        User user = getUserByLogin(userDetails.getUsername());
        Order order = new Order();
        order.setAddress(request.getAddress());
        order.setStatus(OrderStatus.CONFIRMATION);
        order.setUser(user);
        order.setCourier(null);

        Map<Dish, Integer> dishes = new HashMap<>();
        for (Map.Entry<UUID, Integer> entry : request.getDishes().entrySet()) {
            Dish dish = dishRepository.findById(entry.getKey())
                    .orElseThrow(() -> new NotFoundException("Блюдо не найдено"));
            dishes.put(dish, entry.getValue());
        }
        order.setDishes(dishes);

        Order saved = orderRepository.save(order);
        return toOrderResponse(saved);
    }

    @Override
    public OrderResponse updateOrderStatus(UUID id, UpdateStatusRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заказ не найден"));
        order.setStatus(OrderStatus.valueOf(request.getStatus()));
        return toOrderResponse(orderRepository.save(order));
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toOrderResponse)
                .collect(Collectors.toList());
    }

    private User getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    private OrderResponse toOrderResponse(Order order) {
        Map<DishResponse, Integer> dishes = new HashMap<>();
        order.getDishes().forEach((dish, qty) -> dishes.put(toDishResponse(dish), qty));
        UserResponse user = toUserShort(order.getUser());
        UserResponse courier = order.getCourier() != null ? toUserShort(order.getCourier()) : null;
        return new OrderResponse(
                order.getId(),
                order.getCreatedAt() != null ? order.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null,
                order.getAddress(),
                dishes,
                order.getPrice(),
                order.getStatus().name(),
                order.getStatus().getColor(),
                user,
                courier
        );
    }

    private DishResponse toDishResponse(Dish dish) {
        return new DishResponse(
                dish.getId(),
                dish.getName(),
                dish.getImages().stream().map(i -> i.getImageUrl()).collect(Collectors.toList()),
                dish.getComposition(),
                dish.getCcal(),
                dish.getWeight(),
                dish.getPrice(),
                dish.getKitchenType().name(),
                dish.getKitchenType().getEmoji(),
                dish.getDishType().name(),
                dish.getDishType().getEmoji()
        );
    }

    private UserResponse toUserShort(User user) {
        return new UserResponse(
                user.getId(),
                user.getLogin(),
                user.getName(),
                user.getPhone(),
                user.getRole().name(),
                user.getRole().getEmoji(),
                user.getIsWork(),
                null
        );
    }
}

