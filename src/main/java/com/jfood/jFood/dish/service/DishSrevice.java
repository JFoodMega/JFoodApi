package com.jfood.jFood.dish.service;

import com.jfood.jFood.dish.dto.CreateDishDto;
import com.jfood.jFood.dish.dto.ResponseDishDto;
import com.jfood.jFood.dish.dto.UpdateDishDto;

import java.util.List;

public interface DishSrevice {
    ResponseDishDto createDish(CreateDishDto createDishDTO);

    List<ResponseDishDto> getDishes();

    void deleteDish(Long dishId);

    ResponseDishDto updateDish(Long dishId, UpdateDishDto updateDishDTO);
}
