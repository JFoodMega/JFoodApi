package com.jfood.jFood.repository;

import com.jfood.jFood.entity.Address;
import com.jfood.jFood.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

    List<Address> findByUser(User user);
}

