package com.jfood.jFood.courier.repository;

import com.jfood.jFood.courier.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourierRepository extends JpaRepository<Courier, Long> {

    Optional<Courier> findByLogin(String login);

    boolean existsByLogin(String login);

    boolean existsByPhone(String phone);

    boolean existsByLoginAndIdNot(String login, Long id);

    boolean existsByPhoneAndIdNot(String phone, Long id);

    List<Courier> findAllByIsAvailableTrue();

}
