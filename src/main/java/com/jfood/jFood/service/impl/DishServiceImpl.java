package com.jfood.jFood.service.impl;

import com.jfood.jFood.dto.request.CreateDishRequest;
import com.jfood.jFood.dto.request.UpdateDishRequest;
import com.jfood.jFood.dto.response.DishResponse;
import com.jfood.jFood.entity.Dish;
import com.jfood.jFood.entity.DishImage;
import com.jfood.jFood.entity.DishType;
import com.jfood.jFood.entity.KitchenType;
import com.jfood.jFood.exception.NotFoundException;
import com.jfood.jFood.repository.DishImageRepository;
import com.jfood.jFood.repository.DishRepository;
import com.jfood.jFood.service.DishService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishImageRepository dishImageRepository;
    private final Path uploadDir;
    private final String uploadBaseUrl;

    public DishServiceImpl(DishRepository dishRepository,
                           DishImageRepository dishImageRepository,
                           @Value("${upload.path}") String uploadPath,
                           @Value("${upload.base-url}") String uploadBaseUrl) {
        this.dishRepository = dishRepository;
        this.dishImageRepository = dishImageRepository;
        this.uploadDir = Paths.get(uploadPath).toAbsolutePath().normalize();
        this.uploadBaseUrl = uploadBaseUrl;
        try {
            Files.createDirectories(this.uploadDir);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать директорию для загрузки файлов", e);
        }
    }

    @Override
    public List<DishResponse> getDishes(String kitchenType, String dishType) {
        List<Dish> dishes;
        if (StringUtils.hasText(kitchenType) && StringUtils.hasText(dishType)) {
            dishes = dishRepository.findByKitchenTypeAndDishType(
                    KitchenType.valueOf(kitchenType),
                    DishType.valueOf(dishType)
            );
        } else if (StringUtils.hasText(kitchenType)) {
            dishes = dishRepository.findByKitchenType(KitchenType.valueOf(kitchenType));
        } else if (StringUtils.hasText(dishType)) {
            dishes = dishRepository.findByDishType(DishType.valueOf(dishType));
        } else {
            dishes = dishRepository.findAll();
        }
        return dishes.stream().map(this::toDishResponse).collect(Collectors.toList());
    }

    @Override
    public DishResponse getDish(UUID id) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Блюдо не найдено"));
        return toDishResponse(dish);
    }

    @Override
    public DishResponse createDish(CreateDishRequest request) {
        Dish dish = new Dish();
        applyDishFields(dish, request.getName(), request.getComposition(), request.getCcal(),
                request.getWeight(), request.getPrice(), request.getKitchenType(), request.getDishType());
        return toDishResponse(dishRepository.save(dish));
    }

    @Override
    public DishResponse updateDish(UUID id, UpdateDishRequest request) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Блюдо не найдено"));
        applyDishFields(dish,
                request.getName() != null ? request.getName() : dish.getName(),
                request.getComposition() != null ? request.getComposition() : dish.getComposition(),
                request.getCcal() != null ? request.getCcal() : dish.getCcal(),
                request.getWeight() != null ? request.getWeight() : dish.getWeight(),
                request.getPrice() != null ? request.getPrice() : dish.getPrice(),
                request.getKitchenType() != null ? request.getKitchenType() : dish.getKitchenType().name(),
                request.getDishType() != null ? request.getDishType() : dish.getDishType().name()
        );
        return toDishResponse(dishRepository.save(dish));
    }

    @Override
    public void deleteDish(UUID id) {
        if (!dishRepository.existsById(id)) {
            throw new NotFoundException("Блюдо не найдено");
        }
        dishRepository.deleteById(id);
    }

    @Override
    public List<String> uploadImages(UUID id, List<MultipartFile> files) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Блюдо не найдено"));
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path target = uploadDir.resolve(filename);
            try {
                file.transferTo(target.toFile());
            } catch (IOException e) {
                throw new RuntimeException("Ошибка сохранения файла", e);
            }
            String url = uploadBaseUrl + "/" + filename;
            DishImage image = new DishImage(null, url, dish);
            dishImageRepository.save(image);
            urls.add(url);
        }
        return urls;
    }

    @Override
    public void deleteImage(String imageUrl) {
        DishImage image = dishImageRepository.findByImageUrl(imageUrl)
                .orElseThrow(() -> new NotFoundException("Изображение не найдено"));
        dishImageRepository.delete(image);
        // попытка удалить физический файл
        if (imageUrl.startsWith(uploadBaseUrl)) {
            String filename = imageUrl.substring(uploadBaseUrl.length() + 1);
            File file = uploadDir.resolve(filename).toFile();
            if (file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        }
    }

    private void applyDishFields(Dish dish,
                                 String name,
                                 String composition,
                                 Integer ccal,
                                 Integer weight,
                                 Integer price,
                                 String kitchenType,
                                 String dishType) {
        dish.setName(name);
        dish.setComposition(composition);
        dish.setCcal(ccal);
        dish.setWeight(weight);
        dish.setPrice(price);
        dish.setKitchenType(KitchenType.valueOf(kitchenType));
        dish.setDishType(DishType.valueOf(dishType));
    }

    private DishResponse toDishResponse(Dish dish) {
        List<String> images = dish.getImages().stream()
                .map(DishImage::getImageUrl)
                .collect(Collectors.toList());
        return new DishResponse(
                dish.getId(),
                dish.getName(),
                images,
                dish.getComposition(),
                dish.getCcal(),
                dish.getWeight(),
                dish.getPrice(),
                dish.getKitchenType().name(),
                dish.getKitchenType().getEmoji(),
                dish.getDishType().name(),
                dish.getDishType().getEmoji()
        );
    }
}

