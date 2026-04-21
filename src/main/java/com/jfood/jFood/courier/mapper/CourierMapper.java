package com.jfood.jFood.courier.mapper;

import com.jfood.jFood.courier.dto.CourierCreateDto;
import com.jfood.jFood.courier.dto.CourierResponseDto;
import com.jfood.jFood.courier.dto.CourierUpdateDto;
import com.jfood.jFood.courier.model.Courier;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CourierMapper {

    Courier toEntity(CourierCreateDto dto);

    CourierResponseDto toResponseDto(Courier courier);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(CourierUpdateDto dto, @MappingTarget Courier courier);
}
