package com.jfood.jFood.moderator.repository;

import com.jfood.jFood.moderator.model.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModeratorRepository extends JpaRepository<Moderator, Long> {
    Optional<Moderator> findByLogin(String login);
}
