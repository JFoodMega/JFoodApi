package com.jfood.jFood.address.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @NotBlank
    private String house;

    private int appartments;

    private int floor;

    private int entrance;
}
