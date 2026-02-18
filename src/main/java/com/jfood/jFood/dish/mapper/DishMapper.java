package com.jfood.jFood.dish.mapper;

import com.jfood.jFood.dish.dto.CreateDishDto;
import com.jfood.jFood.dish.dto.ResponseDishDto;
import com.jfood.jFood.dish.dto.UpdateDishDto;
import com.jfood.jFood.dish.model.Dish;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DishMapper {
    Dish mapCreateDishDtoToDish(CreateDishDto createDishDTO);

    ResponseDishDto mapDishToResponseDishDto(Dish dish);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDishFromDto(UpdateDishDto dto, @MappingTarget Dish dish);

}
