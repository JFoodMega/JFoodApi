package com.jfood.jFood.client.service;

import com.jfood.jFood.address.dto.AddressDto;
import com.jfood.jFood.address.dto.UpdateAddressDto;
import com.jfood.jFood.address.mapper.AddressMapper;
import com.jfood.jFood.address.model.Address;
import com.jfood.jFood.address.repository.AddressRepository;
import com.jfood.jFood.client.dto.CreateClientDto;
import com.jfood.jFood.client.dto.ResponseClientDto;
import com.jfood.jFood.client.dto.UpdateClientDto;
import com.jfood.jFood.client.mapper.ClientMapper;
import com.jfood.jFood.client.model.Client;
import com.jfood.jFood.client.repository.ClientRepository;
import com.jfood.jFood.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    private final ClientMapper mapper;
    private final AddressMapper addressMapper;

    @Override
    @Transactional
    public ResponseClientDto createClient(CreateClientDto createClientDto){
        Client clientEntity = mapper.mapCreateClientDtoToClient(createClientDto);

        Client savedClient = clientRepository.save(clientEntity);

        return mapper.mapClientToResponseClientDto(savedClient);

    }

    @Override
    public List<ResponseClientDto> getClients(){
        List<Client> clients = clientRepository.findAll();

        return clients.stream()
                .map(mapper::mapClientToResponseClientDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteClient(Long clientId){
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new NotFoundException("Client with id=" + clientId + " was not found"));
        clientRepository.delete(client);
    }

    @Override
    @Transactional
    public ResponseClientDto updateClient(Long clientId, UpdateClientDto updateDto) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new NotFoundException("Client with id=" + clientId + " was not found"));

        mapper.updateClientFromDto(updateDto, client);

        Client savedClient = clientRepository.save(client);

        return mapper.mapClientToResponseClientDto(savedClient);
    }

    @Override
    @Transactional
    public AddressDto addAddress(Long clientId, UpdateAddressDto addressDto) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        Address address = addressMapper.mapToAddress(addressDto);
        address.setClient(client);

        Address savedAddress = addressRepository.save(address);
        return addressMapper.mapToAddressDto(savedAddress);
    }

    @Override
    @Transactional
    public AddressDto updateClientAddress(Long clientId, Long addressId, UpdateAddressDto addressDto) {
        if (!clientRepository.existsById(clientId)) {
            throw new NotFoundException("Client not found");
        }

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException("Address not found"));

        if (!address.getClient().getId().equals(clientId)) {
            throw new RuntimeException("This address does not belong to this client");
        }

        addressMapper.updateAddressFromDto(addressDto, address);
        return addressMapper.mapToAddressDto(addressRepository.save(address));
    }

    @Override
    @Transactional
    public void deleteAddress(Long clientId, Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException("Address not found"));

        if (!address.getClient().getId().equals(clientId)) {
            throw new RuntimeException("Access denied");
        }

        addressRepository.delete(address);
    }

    @Override
    public List<AddressDto> getClientAddresses(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        return client.getAddresses().stream()
                .map(addressMapper::mapToAddressDto)
                .collect(Collectors.toList());
    }

}
