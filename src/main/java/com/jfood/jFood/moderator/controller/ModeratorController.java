package com.jfood.jFood.moderator.controller;

import com.jfood.jFood.moderator.dto.ModeratorCreateDto;
import com.jfood.jFood.moderator.dto.ModeratorResponseDto;
import com.jfood.jFood.moderator.dto.ModeratorUpdateDto;
import com.jfood.jFood.moderator.service.ModeratorService;
import com.jfood.jFood.order.dto.ResponseOrderDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moderators")
@RequiredArgsConstructor
public class ModeratorController {

    private final ModeratorService moderatorService;

    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public ModeratorResponseDto create(@Valid @RequestBody ModeratorCreateDto dto) {
        return moderatorService.create(dto);
    }

    @GetMapping("/{moderatorId}")
    public ModeratorResponseDto getById(@PathVariable Long moderatorId) {
        return moderatorService.getById(moderatorId);
    }

    @PatchMapping("/{moderatorId}")
    public ModeratorResponseDto update(@PathVariable Long moderatorId,
                                       @RequestBody ModeratorUpdateDto dto) {
        return moderatorService.update(moderatorId, dto);
    }

    @DeleteMapping("/admin/{moderatorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long moderatorId) {
        moderatorService.delete(moderatorId);
    }

    @GetMapping("/orders")
    public List<ResponseOrderDto> getAllOrders() {
        return moderatorService.getAllOrders();
    }

    @PostMapping("/orders/{orderId}/assign")
    public ResponseOrderDto assignCourier(@PathVariable Long orderId,
                                          @RequestParam Long courierId,
                                          @RequestParam Long moderatorId) {
        return moderatorService.assignCourier(orderId, courierId, moderatorId);
    }

    @GetMapping("/admin")
    public List<ModeratorResponseDto> getAll() {
        return moderatorService.getAll();
    }
}
