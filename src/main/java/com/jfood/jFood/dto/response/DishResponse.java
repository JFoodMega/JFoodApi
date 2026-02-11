package com.jfood.jFood.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishResponse {

    private UUID id;
    private String name;
    private List<String> images;
    private String composition;
    private Integer ccal;
    private Integer weight;
    private Integer price;
    private String kitchenType;
    private String kitchenTypeEmoji;
    private String dishType;
    private String dishTypeEmoji;
}

