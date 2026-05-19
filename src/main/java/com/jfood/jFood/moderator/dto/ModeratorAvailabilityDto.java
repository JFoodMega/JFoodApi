package com.jfood.jFood.moderator.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModeratorAvailabilityDto {
    @NotNull
    private Boolean isOnline;
}
