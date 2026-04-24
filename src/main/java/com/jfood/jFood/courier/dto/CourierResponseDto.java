package com.jfood.jFood.courier.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierResponseDto {
    private Long id;
    private String name;
    private String phone;
    private Boolean isAvailable;
    private Integer totalDeliveries;
    private Integer totalEarnings;
}