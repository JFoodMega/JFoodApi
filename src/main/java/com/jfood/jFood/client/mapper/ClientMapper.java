package com.jfood.jFood.client.mapper;

import com.jfood.jFood.client.dto.CreateClientDto;
import com.jfood.jFood.client.dto.ResponseClientDto;
import com.jfood.jFood.client.dto.UpdateClientDto;
import com.jfood.jFood.client.model.Client;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client mapCreateClientDtoToClient(CreateClientDto createClientDto);

    @Mapping(target = "ordersCount", expression = "java(client.getOrders().size())")
    ResponseClientDto mapClientToResponseClientDto(Client client);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateClientFromDto(UpdateClientDto dto, @MappingTarget Client client);
}
