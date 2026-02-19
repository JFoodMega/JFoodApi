package com.jfood.jFood.address.mapper;

import com.jfood.jFood.address.dto.AddressDto;
import com.jfood.jFood.address.dto.UpdateAddressDto;
import com.jfood.jFood.address.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto mapToAddressDto(Address address);

    @Mapping(target = "id", ignore = true)
    Address mapToAddress(UpdateAddressDto updateAddressDto);

    UpdateAddressDto mapToUpdateAddressDto(Address address);

    @Mapping(target = "id", ignore = true)
    void updateAddressFromDto(UpdateAddressDto dto, @MappingTarget Address address);
}
