package com.jfood.jFood.dish.controller;

import com.jfood.jFood.dish.dto.CreateDishDTO;
import com.jfood.jFood.dish.dto.ResponseDishDTO;
import com.jfood.jFood.dish.dto.UpdateDishDTO;
import com.jfood.jFood.dish.service.DishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDishDTO createDish(@Valid @RequestBody CreateDishDTO createDto) {
        return dishService.createDish(createDto);
    }

    @PatchMapping("/{dishId}")
    public ResponseDishDTO updateDish(@PathVariable Long dishId,
                                      @RequestBody UpdateDishDTO updateDto) {
        return dishService.updateDish(dishId, updateDto);
    }

    @DeleteMapping("/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable Long dishId) {
        dishService.deleteDish(dishId);
    }

    @GetMapping
    public List<ResponseDishDTO> getDishes() {
        return dishService.getDishes();
    }

}

