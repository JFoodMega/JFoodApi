package com.jfood.jFood.courier.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierAvailabilityDto {
    @NotNull
    private Boolean isAvailable;
}
