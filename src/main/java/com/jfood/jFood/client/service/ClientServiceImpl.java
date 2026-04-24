package com.jfood.jFood.client.service;

import com.jfood.jFood.address.dto.AddressDto;
import com.jfood.jFood.address.dto.UpdateAddressDto;
import com.jfood.jFood.address.mapper.AddressMapper;
import com.jfood.jFood.address.model.Address;
import com.jfood.jFood.address.repository.AddressRepository;
import com.jfood.jFood.client.dto.CreateClientDto;
import com.jfood.jFood.client.dto.LogInClientDto;
import com.jfood.jFood.client.dto.ResponseClientDto;
import com.jfood.jFood.client.dto.UpdateClientDto;
import com.jfood.jFood.client.mapper.ClientMapper;
import com.jfood.jFood.client.model.Client;
import com.jfood.jFood.client.repository.ClientRepository;
import com.jfood.jFood.exception.NotFoundException;
import com.jfood.jFood.order.dto.ResponseOrderDto;
import com.jfood.jFood.order.mapper.OrderMapper;
import com.jfood.jFood.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public ResponseClientDto createClient(CreateClientDto createClientDto) {
        Client clientEntity = mapper.mapCreateClientDtoToClient(createClientDto);
        return mapper.mapClientToResponseClientDto(clientRepository.save(clientEntity));
    }

    @Override
    public Page<ResponseClientDto> getClients(String search, Pageable pageable) {
        return clientRepository.findBySearch(search, pageable)
                .map(mapper::mapClientToResponseClientDto);
    }

    @Override
    public ResponseClientDto getClientById(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Клиент с id=" + clientId + " не найден"));
        return mapper.mapClientToResponseClientDto(client);
    }

    @Override
    @Transactional
    public void deleteClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Клиент с id=" + clientId + " не найден"));
        clientRepository.delete(client);
    }

    @Override
    @Transactional
    public ResponseClientDto updateClient(Long clientId, UpdateClientDto updateDto) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Клиент с id=" + clientId + " не найден"));
        mapper.updateClientFromDto(updateDto, client);
        return mapper.mapClientToResponseClientDto(clientRepository.save(client));
    }

    @Override
    @Transactional
    public AddressDto addAddress(Long clientId, UpdateAddressDto addressDto) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Клиент не найден"));
        Address address = addressMapper.mapToAddress(addressDto);
        address.setClient(client);
        return addressMapper.mapToAddressDto(addressRepository.save(address));
    }

    @Override
    @Transactional
    public AddressDto updateClientAddress(Long clientId, Long addressId, UpdateAddressDto addressDto) {
        if (!clientRepository.existsById(clientId)) {
            throw new NotFoundException("Клиент не найден");
        }
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException("Адрес не найден"));
        if (!address.getClient().getId().equals(clientId)) {
            throw new RuntimeException("Этот адрес не принадлежит клиенту");
        }
        addressMapper.updateAddressFromDto(addressDto, address);
        return addressMapper.mapToAddressDto(addressRepository.save(address));
    }

    @Override
    @Transactional
    public void deleteAddress(Long clientId, Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException("Адрес не найден"));
        if (!address.getClient().getId().equals(clientId)) {
            throw new RuntimeException("Нет доступа");
        }
        addressRepository.delete(address);
    }

    @Override
    public List<AddressDto> getClientAddresses(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Клиент не найден"));
        return client.getAddresses().stream()
                .map(addressMapper::mapToAddressDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseClientDto login(LogInClientDto dto) {
        Client client = clientRepository.findByLogin(dto.getLogin())
                .orElseThrow(() -> new NotFoundException("Неверный логин или пароль"));
        if (!client.getPassword().equals(dto.getPassword())) {
            throw new NotFoundException("Неверный логин или пароль");
        }
        ResponseClientDto response = mapper.mapClientToResponseClientDto(client);
        response.setAddresses(
                client.getAddresses().stream()
                        .map(addressMapper::mapToAddressDto)
                        .toList()
        );
        return response;
    }

    @Override
    public Page<ResponseOrderDto> getClientOrders(Long clientId, Pageable pageable) {
        if (!clientRepository.existsById(clientId)) {
            throw new NotFoundException("Клиент с id=" + clientId + " не найден");
        }
        return orderRepository.findByClientId(clientId, pageable)
                .map(orderMapper::toResponseDto);
    }
}