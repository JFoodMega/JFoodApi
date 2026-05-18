package com.jfood.jFood.courier.service;

import com.jfood.jFood.client.repository.ClientRepository;
import com.jfood.jFood.courier.dto.CourierAvailabilityDto;
import com.jfood.jFood.courier.dto.CourierCreateDto;
import com.jfood.jFood.courier.dto.CourierResponseDto;
import com.jfood.jFood.courier.dto.CourierUpdateDto;
import com.jfood.jFood.courier.mapper.CourierMapper;
import com.jfood.jFood.courier.model.Courier;
import com.jfood.jFood.courier.repository.CourierRepository;
import com.jfood.jFood.exception.AlreadyExistsException;
import com.jfood.jFood.exception.NotFoundException;
import com.jfood.jFood.moderator.repository.ModeratorRepository;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;
    private final ClientRepository clientRepository;
    private final ModeratorRepository moderatorRepository;
    private final OrderRepository orderRepository;
    private final CourierMapper courierMapper;
    private final OrderMapper orderMapper;

    @Value("${courier.delivery.price}")
    private Integer deliveryPrice;

    @Override
    @Transactional
    public CourierResponseDto create(CourierCreateDto dto) {
        if (courierRepository.existsByLogin(dto.getLogin())
                || clientRepository.existsByLogin(dto.getLogin())
                || moderatorRepository.existsByLogin(dto.getLogin())) {
            throw new AlreadyExistsException("Пользователь с логином «" + dto.getLogin() + "» уже существует");
        }
        if (courierRepository.existsByPhone(dto.getPhone())
                || clientRepository.existsByPhone(dto.getPhone())
                || moderatorRepository.existsByPhone(dto.getPhone())) {
            throw new AlreadyExistsException("Пользователь с телефоном «" + dto.getPhone() + "» уже существует");
        }
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
    public List<CourierResponseDto> getAll() {
        return courierRepository.findAll()
                .stream()
                .map(c -> enrichWithStats(courierMapper.toResponseDto(c), c.getId()))
                .toList();
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
        if (dto.getLogin() != null && (courierRepository.existsByLoginAndIdNot(dto.getLogin(), id)
                || clientRepository.existsByLogin(dto.getLogin())
                || moderatorRepository.existsByLogin(dto.getLogin()))) {
            throw new AlreadyExistsException("Пользователь с логином «" + dto.getLogin() + "» уже существует");
        }
        if (dto.getPhone() != null && (courierRepository.existsByPhoneAndIdNot(dto.getPhone(), id)
                || clientRepository.existsByPhone(dto.getPhone())
                || moderatorRepository.existsByPhone(dto.getPhone()))) {
            throw new AlreadyExistsException("Пользователь с телефоном «" + dto.getPhone() + "» уже существует");
        }
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

        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        int todayDeliveries = orderRepository.countByCourierIdAndStatusAndUpdatedAtAfter(
                courierId, OrderStatus.DELIVERED, startOfToday);
        dto.setTodayDeliveries(todayDeliveries);
        dto.setTodayEarnings(todayDeliveries * deliveryPrice);

        return dto;
    }
}