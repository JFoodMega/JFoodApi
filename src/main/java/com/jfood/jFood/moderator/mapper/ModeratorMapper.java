package com.jfood.jFood.moderator.mapper;

import com.jfood.jFood.moderator.dto.ModeratorCreateDto;
import com.jfood.jFood.moderator.dto.ModeratorResponseDto;
import com.jfood.jFood.moderator.dto.ModeratorUpdateDto;
import com.jfood.jFood.moderator.model.Moderator;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ModeratorMapper {

    Moderator toEntity(ModeratorCreateDto dto);

    ModeratorResponseDto toResponseDto(Moderator moderator);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ModeratorUpdateDto dto, @MappingTarget Moderator moderator);
}
