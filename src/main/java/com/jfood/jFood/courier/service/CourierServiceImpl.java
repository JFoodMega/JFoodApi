package com.jfood.jFood.courier.service;

import com.jfood.jFood.courier.dto.CourierAvailabilityDto;
import com.jfood.jFood.courier.dto.CourierCreateDto;
import com.jfood.jFood.courier.dto.CourierResponseDto;
import com.jfood.jFood.courier.dto.CourierUpdateDto;
import com.jfood.jFood.courier.mapper.CourierMapper;
import com.jfood.jFood.courier.model.Courier;
import com.jfood.jFood.courier.repository.CourierRepository;
import com.jfood.jFood.exception.NotFoundException;
import com.jfood.jFood.order.dto.ResponseOrderDto;
import com.jfood.jFood.order.mapper.OrderMapper;
import com.jfood.jFood.order.model.OrderStatus;
import com.jfood.jFood.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;
    private final OrderRepository orderRepository;
    private final CourierMapper courierMapper;
    private final OrderMapper orderMapper;

    @Value("${courier.delivery.price}")
    private Integer deliveryPrice;

    @Override
    @Transactional
    public CourierResponseDto create(CourierCreateDto dto) {
        Courier courier = courierMapper.toEntity(dto);
        return enrichWithStats(courierMapper.toResponseDto(courierRepository.save(courier)), courier.getId());
    }

    @Override
    public CourierResponseDto getById(Long id) {
        Courier courier = courierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Курьер не найден: " + id));
        return enrichWithStats(courierMapper.toResponseDto(courier), id);
    }

    @Override
    public List<CourierResponseDto> getAllAvailable() {
        return courierRepository.findAllByIsAvailableTrue()
                .stream()
                .map(c -> enrichWithStats(courierMapper.toResponseDto(c), c.getId()))
                .toList();
    }

    @Override
    @Transactional
    public CourierResponseDto updateAvailability(Long id, CourierAvailabilityDto dto) {
        Courier courier = courierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Курьер не найден: " + id));
        courier.setIsAvailable(dto.getIsAvailable());
        return enrichWithStats(courierMapper.toResponseDto(courier), id);
    }

    @Override
    @Transactional
    public CourierResponseDto update(Long id, CourierUpdateDto dto) {
        Courier courier = courierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Курьер не найден: " + id));
        courierMapper.updateFromDto(dto, courier);
        return enrichWithStats(courierMapper.toResponseDto(courier), id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!courierRepository.existsById(id)) {
            throw new NotFoundException("Курьер не найден: " + id);
        }
        courierRepository.deleteById(id);
    }

    @Override
    public Page<ResponseOrderDto> getMyOrders(Long courierId, Pageable pageable) {
        return orderRepository.findByCourierId(courierId, pageable)
                .map(orderMapper::toResponseDto);
    }

    private CourierResponseDto enrichWithStats(CourierResponseDto dto, Long courierId) {
        int totalDeliveries = orderRepository.countByCourierIdAndStatus(courierId, OrderStatus.DELIVERED);
        dto.setTotalDeliveries(totalDeliveries);
        dto.setTotalEarnings(totalDeliveries * deliveryPrice);
        return dto;
    }
}