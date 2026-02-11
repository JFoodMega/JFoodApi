package com.jfood.jFood.controller;

import com.jfood.jFood.dto.request.CreateDishRequest;
import com.jfood.jFood.dto.request.UpdateDishRequest;
import com.jfood.jFood.dto.response.DishResponse;
import com.jfood.jFood.service.DishService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dishes")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public ResponseEntity<List<DishResponse>> getDishes(
            @RequestParam(required = false) String kitchenType,
            @RequestParam(required = false) String dishType) {
        return ResponseEntity.ok(dishService.getDishes(kitchenType, dishType));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishResponse> getDish(@PathVariable UUID id) {
        return ResponseEntity.ok(dishService.getDish(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<DishResponse> createDish(
            @Valid @RequestBody CreateDishRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dishService.createDish(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<DishResponse> updateDish(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateDishRequest request) {
        return ResponseEntity.ok(dishService.updateDish(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDish(@PathVariable UUID id) {
        dishService.deleteDish(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/images")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<List<String>> uploadImages(
            @PathVariable UUID id,
            @RequestParam("files") List<MultipartFile> files) {
        return ResponseEntity.ok(dishService.uploadImages(id, files));
    }

    @DeleteMapping("/images/{imageUrl}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Void> deleteImage(@PathVariable String imageUrl) {
        dishService.deleteImage(imageUrl);
        return ResponseEntity.noContent().build();
    }
}

