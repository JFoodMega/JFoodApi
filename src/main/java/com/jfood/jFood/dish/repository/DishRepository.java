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
            AND (:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%')))
            """)
    List<Dish> findAllWithFilters(
            @Param("cuisineType") CuisineType cuisineType,
            @Param("dishType") DishType dishType,
            @Param("name") String name);

    @Query("""
            SELECT d FROM Dish d
            WHERE (:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%')))
            ORDER BY d.id DESC
            """)
    List<Dish> findAllIncludingInactive(@Param("name") String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    @Query("SELECT COUNT(o) > 0 FROM Order o JOIN o.dishes d WHERE d.id = :dishId")
    boolean isUsedInOrders(@Param("dishId") Long dishId);
}