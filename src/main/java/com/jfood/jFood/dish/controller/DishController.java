package com.jfood.jFood.dish.controller;

import com.jfood.jFood.dish.dto.CreateDishDto;
import com.jfood.jFood.dish.dto.ResponseDishDto;
import com.jfood.jFood.dish.dto.UpdateDishDto;
import com.jfood.jFood.dish.service.DishSrevice;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
public class DishController {
    private final DishSrevice dishService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDishDto createDish(@Valid @RequestBody CreateDishDto createDto) {
        return dishService.createDish(createDto);
    }

    @PatchMapping("/{dishId}")
    public ResponseDishDto updateDish(@PathVariable Long dishId,
                                      @RequestBody UpdateDishDto updateDto) {
        return dishService.updateDish(dishId, updateDto);
    }

    @DeleteMapping("/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable Long dishId) {
        dishService.deleteDish(dishId);
    }

    @GetMapping
    public List<ResponseDishDto> getDishes() {
        return dishService.getDishes();
    }
}
