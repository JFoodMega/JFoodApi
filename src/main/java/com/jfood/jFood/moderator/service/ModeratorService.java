package com.jfood.jFood.moderator.service;

import com.jfood.jFood.moderator.dto.ModeratorCreateDto;
import com.jfood.jFood.moderator.dto.ModeratorResponseDto;
import com.jfood.jFood.moderator.dto.ModeratorUpdateDto;
import com.jfood.jFood.order.dto.ResponseOrderDto;

import java.util.List;

public interface ModeratorService {

    ModeratorResponseDto create(ModeratorCreateDto dto);

    ModeratorResponseDto getById(Long id);

    ModeratorResponseDto update(Long id, ModeratorUpdateDto dto);

    void delete(Long id);

    List<ResponseOrderDto> getAllOrders();

    ResponseOrderDto assignCourier(Long orderId, Long courierId, Long moderatorId);

    List<ModeratorResponseDto> getAll();
}
