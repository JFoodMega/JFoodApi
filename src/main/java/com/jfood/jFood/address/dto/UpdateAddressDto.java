package com.jfood.jFood.address.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateAddressDto {
    private String city;

    @NotBlank
    private String street;

    private String house;

    private int appartments;

    private int floor;

    private int entrance;
}
