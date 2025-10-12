package com.example.bankcards.repository;

import com.example.bankcards.entity.CardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CardRepository extends JpaRepository <CardEntity, Long>, JpaSpecificationExecutor<CardEntity> {

    Optional<CardEntity> findByCardNumber(String cardNumber);

    boolean existsByCardNumber(String cardNumber);

    Page<CardEntity> findAllByUser_Id(Long userId, Pageable pageable);
}
