package com.jfood.jFood.repository;

import com.jfood.jFood.entity.DishImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DishImageRepository extends JpaRepository<DishImage, UUID> {

    Optional<DishImage> findByImageUrl(String imageUrl);
}

