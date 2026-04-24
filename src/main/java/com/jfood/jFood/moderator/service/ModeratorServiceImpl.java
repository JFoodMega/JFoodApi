package com.jfood.jFood.moderator.service;

import com.jfood.jFood.courier.model.Courier;
import com.jfood.jFood.courier.repository.CourierRepository;
import com.jfood.jFood.exception.NotFoundException;
import com.jfood.jFood.moderator.dto.ModeratorCreateDto;
import com.jfood.jFood.moderator.dto.ModeratorResponseDto;
import com.jfood.jFood.moderator.dto.ModeratorUpdateDto;
import com.jfood.jFood.moderator.mapper.ModeratorMapper;
import com.jfood.jFood.moderator.model.Moderator;
import com.jfood.jFood.moderator.repository.ModeratorRepository;
import com.jfood.jFood.order.dto.ResponseOrderDto;
import com.jfood.jFood.order.mapper.OrderMapper;
import com.jfood.jFood.order.model.Order;
import com.jfood.jFood.order.model.OrderStatus;
import com.jfood.jFood.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ModeratorServiceImpl implements ModeratorService {

    private final ModeratorRepository moderatorRepository;
    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;
    private final ModeratorMapper moderatorMapper;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public ModeratorResponseDto create(ModeratorCreateDto dto) {
        Moderator moderator = moderatorMapper.toEntity(dto);
        return moderatorMapper.toResponseDto(moderatorRepository.save(moderator));
    }

    @Override
    public ModeratorResponseDto getById(Long id) {
        Moderator moderator = moderatorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Модератор не найден: " + id));
        return moderatorMapper.toResponseDto(moderator);
    }

    @Override
    @Transactional
    public ModeratorResponseDto update(Long id, ModeratorUpdateDto dto) {
        Moderator moderator = moderatorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Модератор не найден: " + id));
        moderatorMapper.updateFromDto(dto, moderator);
        return moderatorMapper.toResponseDto(moderator);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!moderatorRepository.existsById(id)) {
            throw new NotFoundException("Модератор не найден: " + id);
        }
        moderatorRepository.deleteById(id);
    }

    @Override
    public List<ResponseOrderDto> getNewOrders() {
        return orderRepository.findByStatus(OrderStatus.CREATED)
                .stream()
                .map(orderMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public ResponseOrderDto assignCourier(Long orderId, Long courierId, Long moderatorId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Заказ не найден: " + orderId));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new IllegalStateException("Заказ уже назначен или недоступен");
        }

        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NotFoundException("Курьер не найден: " + courierId));

        if (!courier.getIsAvailable()) {
            throw new IllegalStateException("Курьер недоступен");
        }

        Moderator moderator = moderatorRepository.findById(moderatorId)
                .orElseThrow(() -> new NotFoundException("Модератор не найден: " + moderatorId));

        order.setCourier(courier);
        order.setModerator(moderator);
        order.setAssignedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.COURIER_ASSIGNED);
        courier.setIsAvailable(false);

        return orderMapper.toResponseDto(orderRepository.save(order));
    }

    @Override
    public List<ModeratorResponseDto> getAll() {
        return moderatorRepository.findAll()
                .stream()
                .map(moderatorMapper::toResponseDto)
                .toList();
    }
}