package com.jfood.jFood.dish.controller;

import com.jfood.jFood.dish.dto.CreateDishDto;
import com.jfood.jFood.dish.dto.ResponseDishDto;
import com.jfood.jFood.dish.dto.UpdateDishDto;
import com.jfood.jFood.dish.dto.UpdateDishStatusDto;
import com.jfood.jFood.dish.model.CuisineType;
import com.jfood.jFood.dish.model.DishType;
import com.jfood.jFood.dish.service.DishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishes")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDishDto createDish(@Valid @RequestBody CreateDishDto createDto) {
        return dishService.createDish(createDto);
    }

    @GetMapping
    public List<ResponseDishDto> getDishes(
            @RequestParam(required = false) CuisineType cuisineType,
            @RequestParam(required = false) DishType dishType,
            @RequestParam(required = false) String name) {
        return dishService.getDishes(cuisineType, dishType, name);
    }

    @GetMapping("/{dishId}")
    public ResponseDishDto getDishById(@PathVariable Long dishId) {
        return dishService.getDishById(dishId);
    }

    @PatchMapping("/admin/{dishId}")
    public ResponseDishDto updateDish(@PathVariable Long dishId,
                                      @Valid @RequestBody UpdateDishDto updateDto) {
        return dishService.updateDish(dishId, updateDto);
    }

    @PatchMapping("/admin/{dishId}/status")
    public ResponseDishDto updateDishStatus(@PathVariable Long dishId,
                                            @Valid @RequestBody UpdateDishStatusDto updateDishStatusDto) {
        return dishService.updateDishStatus(dishId, updateDishStatusDto);
    }

    @DeleteMapping("/admin/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable Long dishId) {
        dishService.deleteDish(dishId);
    }
}
