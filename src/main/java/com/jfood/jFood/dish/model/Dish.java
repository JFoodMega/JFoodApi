package com.jfood.jFood.dish.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dishes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "category_id", nullable = false)
    //private Category category;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private Integer price;

    @Column(name = "weight_volume", nullable = false, length = 60)
    private String weightVolume;

    @Column(name = "image_url", length = 300)
    private String imageUrl;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    //@Column(name = "sort_order", nullable = false)
    //private int sortOrder = 0;

    //@Column(name = "popularity", nullable = false)
    //private int popularity = 0;

    //@Column(name = "created_at", nullable = false, updatable = false)
    //private OffsetDateTime createdAt;

    //@Column(name = "updated_at", nullable = false)
    //private DateTime updatedAt;
}