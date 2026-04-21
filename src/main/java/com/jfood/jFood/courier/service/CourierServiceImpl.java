package com.jfood.jFood.courier.service;

import com.jfood.jFood.courier.dto.CourierAvailabilityDto;
import com.jfood.jFood.courier.dto.CourierCreateDto;
import com.jfood.jFood.courier.dto.CourierResponseDto;
import com.jfood.jFood.courier.dto.CourierUpdateDto;
import com.jfood.jFood.courier.mapper.CourierMapper;
import com.jfood.jFood.courier.model.Courier;
import com.jfood.jFood.courier.repository.CourierRepository;
import com.jfood.jFood.order.dto.ResponseOrderDto;
import com.jfood.jFood.order.mapper.OrderMapper;
import com.jfood.jFood.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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

    @Override
    @Transactional
    public CourierResponseDto create(CourierCreateDto dto) {
        Courier courier = courierMapper.toEntity(dto);
        return courierMapper.toResponseDto(courierRepository.save(courier));
    }

    @Override
    public CourierResponseDto getById(Long id) {
        Courier courier = courierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Курьер не найден: " + id));
        return courierMapper.toResponseDto(courier);
    }

    @Override
    public List<CourierResponseDto> getAllAvailable() {
        return courierRepository.findAllByIsAvailableTrue()
                .stream()
                .map(courierMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public CourierResponseDto updateAvailability(Long id, CourierAvailabilityDto dto) {
        Courier courier = courierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Курьер не найден: " + id));
        courier.setIsAvailable(dto.getIsAvailable());
        return courierMapper.toResponseDto(courier);
    }

    @Override
    @Transactional
    public CourierResponseDto update(Long id, CourierUpdateDto dto) {
        Courier courier = courierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Курьер не найден: " + id));
        courierMapper.updateFromDto(dto, courier);
        return courierMapper.toResponseDto(courier);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!courierRepository.existsById(id)) {
            throw new EntityNotFoundException("Курьер не найден: " + id);
        }
        courierRepository.deleteById(id);
    }

    @Override
    public List<ResponseOrderDto> getMyOrders(Long courierId) {
        return orderRepository.findByCourierId(courierId)
                .stream()
                .map(orderMapper::toResponseDto)
                .toList();
    }
}
