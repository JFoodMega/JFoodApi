package com.jfood.jFood.order.model;

import com.jfood.jFood.address.model.Address;
import com.jfood.jFood.client.model.Client;
import com.jfood.jFood.dish.model.Dish;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private List<Dish> dishes = new ArrayList<>();
    private Client client;
    private LocalDateTime time;
    private List<Address> address = new ArrayList<>();
}
