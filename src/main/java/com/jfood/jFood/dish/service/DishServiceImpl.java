package com.jfood.jFood.dish.service;

import com.jfood.jFood.dish.dto.CreateDishDto;
import com.jfood.jFood.dish.dto.ResponseDishDto;
import com.jfood.jFood.dish.dto.UpdateDishDto;
import com.jfood.jFood.dish.dto.UpdateDishStatusDto;
import com.jfood.jFood.dish.mapper.DishMapper;
import com.jfood.jFood.dish.model.CuisineType;
import com.jfood.jFood.dish.model.Dish;
import com.jfood.jFood.dish.model.DishExtra;
import com.jfood.jFood.dish.model.DishType;
import com.jfood.jFood.dish.repository.DishRepository;
import com.jfood.jFood.exception.AlreadyExistsException;
import com.jfood.jFood.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishMapper mapper;

    @Override
    @Transactional
    public ResponseDishDto createDish(CreateDishDto createDishDto) {
        if (dishRepository.existsByName(createDishDto.getName())) {
            throw new AlreadyExistsException("Блюдо с названием '" + createDishDto.getName() + "' уже существует");
        }

        Dish dish = mapper.mapCreateDishDtoToDish(createDishDto);
        dish.getExtras().forEach(extra -> extra.setDish(dish));

        return mapper.mapDishToResponseDishDto(dishRepository.save(dish));
    }

    @Override
    public List<ResponseDishDto> getDishes(CuisineType cuisineType, DishType dishType, String name) {
        return dishRepository.findAllWithFilters(cuisineType, dishType, name)
                .stream()
                .map(mapper::mapDishToResponseDishDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseDishDto getDishById(Long dishId) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new NotFoundException("Блюдо с id=" + dishId + " не найдено"));

        return mapper.mapDishToResponseDishDto(dish);
    }

    @Override
    @Transactional
    public void deleteDish(Long dishId) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new NotFoundException("Блюдо с id=" + dishId + " не найдено"));

        dishRepository.delete(dish);
    }

    @Override
    @Transactional
    public ResponseDishDto updateDish(Long dishId, UpdateDishDto updateDishDto) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new NotFoundException("Блюдо с id=" + dishId + " не найдено"));

        mapper.updateDishFromDto(updateDishDto, dish);

        if (updateDishDto.getExtras() != null) {
            dish.getExtras().clear();
            updateDishDto.getExtras().forEach(extraDto -> {
                DishExtra extra = mapper.mapCreateDishExtraDtoToDishExtra(extraDto);
                extra.setDish(dish);
                dish.getExtras().add(extra);
            });
        }

        return mapper.mapDishToResponseDishDto(dishRepository.save(dish));
    }

    @Override
    @Transactional
    public ResponseDishDto updateDishStatus(Long dishId, UpdateDishStatusDto updateDishStatusDto) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new NotFoundException("Блюдо с id=" + dishId + " не найдено"));

        dish.setIsActive(updateDishStatusDto.getIsActive());

        return mapper.mapDishToResponseDishDto(dishRepository.save(dish));
    }
}