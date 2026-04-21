package com.jfood.jFood.client.repository;

import com.jfood.jFood.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByLogin(String login);
}
