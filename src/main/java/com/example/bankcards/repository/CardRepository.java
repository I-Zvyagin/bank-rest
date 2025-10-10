package com.example.bankcards.repository;

import com.example.bankcards.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository <CardEntity, Long> {

    Optional<CardEntity> findByCardNumber(String cardNumber);

    boolean existsByCardNumber(String cardNumber);
}
