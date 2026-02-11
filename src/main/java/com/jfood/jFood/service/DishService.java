package com.jfood.jFood.service;

import com.jfood.jFood.dto.request.CreateDishRequest;
import com.jfood.jFood.dto.request.UpdateDishRequest;
import com.jfood.jFood.dto.response.DishResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface DishService {

    List<DishResponse> getDishes(String kitchenType, String dishType);

    DishResponse getDish(UUID id);

    DishResponse createDish(CreateDishRequest request);

    DishResponse updateDish(UUID id, UpdateDishRequest request);

    void deleteDish(UUID id);

    List<String> uploadImages(UUID id, List<MultipartFile> files);

    void deleteImage(String imageUrl);
}

