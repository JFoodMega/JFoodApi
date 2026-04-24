package com.jfood.jFood.client.repository;

import com.jfood.jFood.client.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByLogin(String login);

    @Query("""
            SELECT c FROM Client c
            WHERE (:search IS NULL OR
                   LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(c.phone) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(c.login) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<Client> findBySearch(@Param("search") String search, Pageable pageable);
}