package com.jfood.jFood.order.service;

import com.jfood.jFood.client.model.Client;
import com.jfood.jFood.client.repository.ClientRepository;
import com.jfood.jFood.dish.mapper.DishMapper;
import com.jfood.jFood.dish.model.Dish;
import com.jfood.jFood.dish.repository.DishRepository;
import com.jfood.jFood.order.dto.CreateOrderDto;
import com.jfood.jFood.order.dto.ResponseOrderDto;
import com.jfood.jFood.order.mapper.OrderMapper;
import com.jfood.jFood.order.model.Order;
import com.jfood.jFood.order.model.OrderStatus;
import com.jfood.jFood.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final DishRepository dishRepository;

    private final OrderMapper orderMapper;
    private final DishMapper dishMapper;

    @Override
    @Transactional(readOnly = false)
    public ResponseOrderDto createOrder(CreateOrderDto dto) {
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found: " + dto.getClientId()));

        List<Dish> dishes = dishRepository.findAllById(dto.getDishIds());
        if (dishes.size() != dto.getDishIds().size()) {
            throw new RuntimeException("Some dishes not found");
        }

        Order order = orderMapper.toEntity(dto);
        order.setClient(client);
        order.getDishes().addAll(dishes);

        int total = dishes.stream()
                .map(Dish::getPrice)
                .filter(p -> p != null)
                .mapToInt(Integer::intValue)
                .sum();
        order.setTotalPrice(total);

        Order saved = orderRepository.save(order);
        return orderMapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseOrderDto getById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
        return orderMapper.toResponseDto(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseOrderDto> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseOrderDto> getByClientId(Long clientId, Pageable pageable) {
        return orderRepository.findByClientId(clientId, pageable)
                .map(orderMapper::toResponseDto);
    }

    @Override
    public ResponseOrderDto updateStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        order.setStatus(status);

        Order saved = orderRepository.save(order);
        return orderMapper.toResponseDto(saved);
    }

    @Override
    public void delete(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found: " + orderId);
        }
        orderRepository.deleteById(orderId);
    }
}
