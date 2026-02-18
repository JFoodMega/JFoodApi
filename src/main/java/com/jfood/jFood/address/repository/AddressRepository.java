package com.jfood.jFood.address.repository;

import com.jfood.jFood.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
