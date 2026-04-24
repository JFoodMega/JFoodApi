package com.jfood.jFood.dish.mapper;

import com.jfood.jFood.dish.dto.*;
import com.jfood.jFood.dish.model.Dish;
import com.jfood.jFood.dish.model.DishExtra;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DishMapper {

    @Mapping(target = "isActive", constant = "true")
    Dish mapCreateDishDtoToDish(CreateDishDto createDishDto);

    ResponseDishDto mapDishToResponseDishDto(Dish dish);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDishFromDto(UpdateDishDto dto, @MappingTarget Dish dish);

    DishExtra mapCreateDishExtraDtoToDishExtra(CreateDishExtraDto createDishExtraDto);

    ResponseDishExtraDto mapDishExtraToResponseDishExtraDto(DishExtra dishExtra);

    List<ResponseDishExtraDto> mapDishExtraListToResponseList(List<DishExtra> extras);
}
