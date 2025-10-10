package com.example.bankcards.service;

import com.example.bankcards.entity.CardEntity;

import java.util.List;

public interface CardService {

    CardEntity createCard(CardEntity card);

    CardEntity saveCard(CardEntity card);

    List<CardEntity> getAllCards();

    List<CardEntity> getCardsForUser(Long id);

    void deleteCard(Long id);
}
