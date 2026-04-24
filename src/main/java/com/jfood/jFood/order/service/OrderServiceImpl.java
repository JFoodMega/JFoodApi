package com.jfood.jFood.order.service;

import com.jfood.jFood.address.model.Address;
import com.jfood.jFood.address.repository.AddressRepository;
import com.jfood.jFood.client.model.Client;
import com.jfood.jFood.client.repository.ClientRepository;
import com.jfood.jFood.dish.mapper.DishMapper;
import com.jfood.jFood.dish.model.Dish;
import com.jfood.jFood.dish.repository.DishRepository;
import com.jfood.jFood.exception.NotFoundException;
import com.jfood.jFood.order.dto.CheckoutOrderDto;
import com.jfood.jFood.order.dto.CreateOrderDto;
import com.jfood.jFood.order.dto.ResponseOrderDto;
import com.jfood.jFood.order.dto.UpdateOrderStatusDto;
import com.jfood.jFood.order.mapper.OrderMapper;
import com.jfood.jFood.order.model.DeliveryType;
import com.jfood.jFood.order.model.Order;
import com.jfood.jFood.order.model.OrderStatus;
import com.jfood.jFood.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final DishRepository dishRepository;
    private final AddressRepository addressRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public ResponseOrderDto createOrder(CreateOrderDto dto) {
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new NotFoundException("Клиент не найден: " + dto.getClientId()));

        List<Dish> dishes = dishRepository.findAllById(dto.getDishIds());
        if (dishes.size() != dto.getDishIds().size()) {
            throw new NotFoundException("Некоторые блюда не найдены");
        }

        Order order = orderMapper.toEntity(dto);
        order.setClient(client);
        order.getDishes().addAll(dishes);
        order.setStatus(OrderStatus.CART);
        order.setTotalPrice(dishes.stream()
                .filter(d -> d.getPrice() != null)
                .mapToInt(Dish::getPrice)
                .sum());

        return orderMapper.toResponseDto(orderRepository.save(order));
    }

    @Override
    @Transactional
    public ResponseOrderDto checkout(Long orderId, CheckoutOrderDto dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Заказ не найден: " + orderId));

        if (order.getStatus() != OrderStatus.CART) {
            throw new IllegalStateException("Заказ уже оформлен");
        }

        Address address = addressRepository.findById(dto.getAddressId())
                .orElseThrow(() -> new NotFoundException("Адрес не найден: " + dto.getAddressId()));

        order.setAddress(address);
        order.setDeliveryType(dto.getDeliveryType());

        if (dto.getDeliveryType() == DeliveryType.ASAP) {
            order.setDeliveryTime(LocalDateTime.now().plusMinutes(30));
        } else {
            if (dto.getDeliveryTime() == null) {
                throw new IllegalArgumentException("Укажите время доставки");
            }
            order.setDeliveryTime(dto.getDeliveryTime());
        }

        order.setStatus(OrderStatus.CREATED);
        return orderMapper.toResponseDto(orderRepository.save(order));
    }

    @Override
    public ResponseOrderDto getById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Заказ не найден: " + orderId));
        return orderMapper.toResponseDto(order);
    }

    @Override
    public Page<ResponseOrderDto> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toResponseDto);
    }

    @Override
    public Page<ResponseOrderDto> getByStatus(OrderStatus status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable)
                .map(orderMapper::toResponseDto);
    }

    @Override
    public Page<ResponseOrderDto> getInProgress(Pageable pageable) {
        return orderRepository.findByStatusIn(
                        List.of(OrderStatus.COURIER_ASSIGNED, OrderStatus.ON_THE_WAY), pageable)
                .map(orderMapper::toResponseDto);
    }

    @Override
    public Page<ResponseOrderDto> getByClientId(Long clientId, Pageable pageable) {
        return orderRepository.findByClientId(clientId, pageable)
                .map(orderMapper::toResponseDto);
    }

    @Override
    @Transactional
    public ResponseOrderDto updateStatus(Long orderId, UpdateOrderStatusDto dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Заказ не найден: " + orderId));

        validateStatusTransition(order.getStatus(), dto.getStatus());

        order.setStatus(dto.getStatus());

        if (dto.getStatus() == OrderStatus.DELIVERED && order.getCourier() != null) {
            order.getCourier().setIsAvailable(true);
        }

        return orderMapper.toResponseDto(orderRepository.save(order));
    }

    @Override
    @Transactional
    public void delete(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new NotFoundException("Заказ не найден: " + orderId);
        }
        orderRepository.deleteById(orderId);
    }

    private void validateStatusTransition(OrderStatus current, OrderStatus next) {
        switch (current) {
            case CART -> {
                if (next != OrderStatus.CANCELED)
                    throw new IllegalStateException("Недопустимый переход: " + current + " -> " + next);
            }
            case CREATED -> {
                if (next != OrderStatus.COURIER_ASSIGNED && next != OrderStatus.CANCELED)
                    throw new IllegalStateException("Недопустимый переход: " + current + " -> " + next);
            }
            case COURIER_ASSIGNED -> {
                if (next != OrderStatus.ON_THE_WAY && next != OrderStatus.CANCELED)
                    throw new IllegalStateException("Недопустимый переход: " + current + " -> " + next);
            }
            case ON_THE_WAY -> {
                if (next != OrderStatus.DELIVERED)
                    throw new IllegalStateException("Недопустимый переход: " + current + " -> " + next);
            }
            default -> throw new IllegalStateException("Нельзя менять статус из: " + current);
        }
    }
}