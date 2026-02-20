package com.jfood.jFood.dish.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DishController {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
        return dishService.createDish(createDto);
    }

    @PatchMapping("/{dishId}")
        return dishService.updateDish(dishId, updateDto);
    }

    @DeleteMapping("/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable Long dishId) {
        dishService.deleteDish(dishId);
    }

    @GetMapping
        return dishService.getDishes();
    }
}
