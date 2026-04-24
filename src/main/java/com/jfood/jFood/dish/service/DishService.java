package com.jfood.jFood.dish.service;

import com.jfood.jFood.dish.dto.CreateDishDto;
import com.jfood.jFood.dish.dto.ResponseDishDto;
import com.jfood.jFood.dish.dto.UpdateDishDto;
import com.jfood.jFood.dish.dto.UpdateDishStatusDto;
import com.jfood.jFood.dish.model.CuisineType;
import com.jfood.jFood.dish.model.DishType;

import java.util.List;

public interface DishService {
    ResponseDishDto createDish(CreateDishDto createDishDto);

    List<ResponseDishDto> getDishes(CuisineType cuisineType, DishType dishType, String name);

    ResponseDishDto getDishById(Long dishId);

    void deleteDish(Long dishId);

    ResponseDishDto updateDish(Long dishId, UpdateDishDto updateDishDto);

    ResponseDishDto updateDishStatus(Long dishId, UpdateDishStatusDto updateDishStatusDto);
}
