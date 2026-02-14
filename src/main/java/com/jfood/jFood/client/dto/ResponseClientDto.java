package com.jfood.jFood.client.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResponseClientDto {
    @NotBlank
    private String phone;
    @NotBlank
    private String login;
    @NotBlank
    private String name;

    //private List<Address> addresses = new ArrayList<>();

    //private List<Order> orders = new ArrayList<>();
}
