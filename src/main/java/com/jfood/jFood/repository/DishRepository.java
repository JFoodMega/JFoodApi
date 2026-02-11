package com.jfood.jFood.repository;

import com.jfood.jFood.entity.Dish;
import com.jfood.jFood.entity.DishType;
import com.jfood.jFood.entity.KitchenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DishRepository extends JpaRepository<Dish, UUID> {

    List<Dish> findByKitchenTypeAndDishType(KitchenType kitchenType, DishType dishType);

    List<Dish> findByKitchenType(KitchenType kitchenType);

    List<Dish> findByDishType(DishType dishType);
}

