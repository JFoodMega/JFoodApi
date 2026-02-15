package com.jfood.jFood.dish.service;

import com.jfood.jFood.dish.dto.CreateDishDTO;
import com.jfood.jFood.dish.dto.ResponseDishDTO;
import com.jfood.jFood.dish.dto.UpdateDishDTO;

import java.util.List;

public interface DishService {
    ResponseDishDTO createDish(CreateDishDTO createDishDTO);

    List<ResponseDishDTO> getDishes();

    void deleteDish(Long dishId);

    ResponseDishDTO updateDish(Long dishId, UpdateDishDTO updateDishDTO);
}
