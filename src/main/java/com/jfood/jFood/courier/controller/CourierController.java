package com.jfood.jFood.courier.controller;

import com.jfood.jFood.courier.dto.CourierAvailabilityDto;
import com.jfood.jFood.courier.dto.CourierCreateDto;
import com.jfood.jFood.courier.dto.CourierResponseDto;
import com.jfood.jFood.courier.dto.CourierUpdateDto;
import com.jfood.jFood.courier.service.CourierService;
import com.jfood.jFood.order.dto.ResponseOrderDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/couriers")
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;

    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public CourierResponseDto create(@Valid @RequestBody CourierCreateDto dto) {
        return courierService.create(dto);
    }

    @GetMapping("/{courierId}")
    public CourierResponseDto getById(@PathVariable Long courierId) {
        return courierService.getById(courierId);
    }

    @GetMapping("/available")
    public List<CourierResponseDto> getAllAvailable() {
        return courierService.getAllAvailable();
    }

    @PatchMapping("/{courierId}/availability")
    public CourierResponseDto updateAvailability(@PathVariable Long courierId,
                                                 @Valid @RequestBody CourierAvailabilityDto dto) {
        return courierService.updateAvailability(courierId, dto);
    }

    @PatchMapping("/{courierId}")
    public CourierResponseDto update(@PathVariable Long courierId,
                                     @Valid @RequestBody CourierUpdateDto dto) {
        return courierService.update(courierId, dto);
    }

    @DeleteMapping("/admin/{courierId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long courierId) {
        courierService.delete(courierId);
    }

    @GetMapping("/{courierId}/orders")
    public Page<ResponseOrderDto> getMyOrders(
            @PathVariable Long courierId,
            Pageable pageable) {
        return courierService.getMyOrders(courierId, pageable);
    }
}