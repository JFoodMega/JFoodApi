package com.jfood.jFood.dish.repository;

import com.jfood.jFood.dish.model.CuisineType;
import com.jfood.jFood.dish.model.Dish;
import com.jfood.jFood.dish.model.DishType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {

    @Query("""
            SELECT d FROM Dish d
            WHERE d.isActive = true
            AND (:#{#cuisineType} IS NULL OR d.cuisineType = :#{#cuisineType})
            AND (:#{#dishType} IS NULL OR d.dishType = :#{#dishType})
            AND (:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%')))
            """)
    List<Dish> findAllWithFilters(
            @Param("cuisineType") CuisineType cuisineType,
            @Param("dishType") DishType dishType,
            @Param("name") String name);

    boolean existsByName(String name);
}