package com.jfood.jFood.address.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    private Long id;

    private String city;

    private String street;

    private String house;

    private int appartments;

    private int floor;

    private int entrance;
}
