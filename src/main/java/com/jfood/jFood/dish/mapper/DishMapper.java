package com.jfood.jFood.dish.mapper;

import com.jfood.jFood.client.dto.ResponseClientDto;
import com.jfood.jFood.dish.dto.CreateDishDTO;
import com.jfood.jFood.dish.dto.ResponseDishDTO;
import com.jfood.jFood.dish.dto.UpdateDishDTO;
import com.jfood.jFood.dish.model.Dish;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DishMapper {
    Dish mapCreateDishDtoToDish(CreateDishDTO createDishDTO);

    ResponseDishDTO mapDishToResponseDishDto(Dish dish);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDishFromDto(UpdateDishDTO dto, @MappingTarget Dish dish);

}
