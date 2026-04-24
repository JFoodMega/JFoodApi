package com.jfood.jFood.order.controller;

import com.jfood.jFood.order.dto.CheckoutOrderDto;
import com.jfood.jFood.order.dto.CreateOrderDto;
import com.jfood.jFood.order.dto.ResponseOrderDto;
import com.jfood.jFood.order.dto.UpdateOrderStatusDto;
import com.jfood.jFood.order.model.OrderStatus;
import com.jfood.jFood.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseOrderDto create(@RequestBody @Valid CreateOrderDto dto) {
        return orderService.createOrder(dto);
    }

    @PostMapping("/{orderId}/checkout")
    public ResponseOrderDto checkout(@PathVariable Long orderId,
                                     @Valid @RequestBody CheckoutOrderDto dto) {
        return orderService.checkout(orderId, dto);
    }

    @GetMapping("/{id}")
    public ResponseOrderDto getById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @GetMapping("/admin")
    public Page<ResponseOrderDto> getAll(Pageable pageable) {
        return orderService.getAll(pageable);
    }

    @GetMapping("/admin/status")
    public Page<ResponseOrderDto> getByStatus(
            @RequestParam OrderStatus status,
            Pageable pageable) {
        return orderService.getByStatus(status, pageable);
    }

    @GetMapping("/admin/in-progress")
    public Page<ResponseOrderDto> getInProgress(Pageable pageable) {
        return orderService.getInProgress(pageable);
    }

    @GetMapping("/client/{clientId}")
    public Page<ResponseOrderDto> getByClientId(@PathVariable Long clientId,
                                                Pageable pageable) {
        return orderService.getByClientId(clientId, pageable);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseOrderDto updateStatus(@PathVariable Long orderId,
                                         @Valid @RequestBody UpdateOrderStatusDto dto) {
        return orderService.updateStatus(orderId, dto);
    }

    @DeleteMapping("/admin/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }
}