package com.jfood.jFood.dish.service;

import com.jfood.jFood.dish.dto.CreateDishDTO;
import com.jfood.jFood.dish.dto.ResponseDishDTO;
import com.jfood.jFood.dish.dto.UpdateDishDTO;
import com.jfood.jFood.dish.mapper.DishMapper;
import com.jfood.jFood.dish.model.Dish;
import com.jfood.jFood.dish.repository.DishRepository;
import com.jfood.jFood.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DishServiceImpl implements DishService{

    private final DishRepository dishRepository;
    DishMapper mapper = Mappers.getMapper(DishMapper.class);


    @Override
    @Transactional
    public ResponseDishDTO createDish(CreateDishDTO createDishDto){
        Dish dishEntity = mapper.mapCreateDishDtoToDish(createDishDto);

        Dish savedDish = dishRepository.save(dishEntity);

        return mapper.mapDishToResponseDishDto(savedDish);
    }

    @Override
    public List<ResponseDishDTO> getDishes(){
        List<Dish> dishes = dishRepository.findAll();

        return dishes.stream()
                .map(mapper::mapDishToResponseDishDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteDish(Long dishId){
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new NotFoundException("Dish with id=" + dishId + " was not found"));
        dishRepository.delete(dish);
    }

    @Override
    @Transactional
    public ResponseDishDTO updateDish(Long dishId, UpdateDishDTO updateDto) {
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new NotFoundException("Dish with id=" + dishId + " was not found"));

        mapper.updateDishFromDto(updateDto, dish);

        Dish savedDish = dishRepository.save(dish);

        return mapper.mapDishToResponseDishDto(savedDish);
    }
}
