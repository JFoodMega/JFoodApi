package com.jfood.jFood.dishes.repository;

import com.jfood.jFood.dishes.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {

}