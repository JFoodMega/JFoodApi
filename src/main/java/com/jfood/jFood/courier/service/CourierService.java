package com.jfood.jFood.courier.service;

import com.jfood.jFood.courier.dto.CourierAvailabilityDto;
import com.jfood.jFood.courier.dto.CourierCreateDto;
import com.jfood.jFood.courier.dto.CourierResponseDto;
import com.jfood.jFood.courier.dto.CourierUpdateDto;
import com.jfood.jFood.order.dto.ResponseOrderDto;

import java.util.List;

public interface CourierService {

    CourierResponseDto create(CourierCreateDto dto);

    CourierResponseDto getById(Long id);

    List<CourierResponseDto> getAllAvailable();

    CourierResponseDto updateAvailability(Long id, CourierAvailabilityDto dto);

    CourierResponseDto update(Long id, CourierUpdateDto dto);

    void delete(Long id);

    List<ResponseOrderDto> getMyOrders(Long courierId);
}

