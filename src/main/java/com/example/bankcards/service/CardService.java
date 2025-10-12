package com.example.bankcards.service;

import com.example.bankcards.entity.CardEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CardService {

    Optional<CardEntity> getCardById(Long id);

    CardEntity createCard(CardEntity card);

    CardEntity saveCard(CardEntity card);

    List<CardEntity> getAllCards();

    List<CardEntity> getCardsForUser(Long id);

    Page<CardEntity> getCardsForUser(Long id, int page, int size);

    void deleteCard(Long id);
}
