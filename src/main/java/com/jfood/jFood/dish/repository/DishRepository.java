package com.jfood.jFood.dish.repository;

import com.jfood.jFood.dish.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {

}