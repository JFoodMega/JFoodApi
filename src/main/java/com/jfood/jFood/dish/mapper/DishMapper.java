package com.jfood.jFood.dish.mapper;

import com.jfood.jFood.client.dto.ResponseClientDto;
import com.jfood.jFood.client.model.Client;
import com.jfood.jFood.dish.dto.CreateDishDTO;
import com.jfood.jFood.dish.model.Dish;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DishMapper {
    Dish mapCreateDishDtoToDish(CreateDishDTO createDishDTO);

    ResponseClientDto mapClientToResponseClientDto(Client client);
}
