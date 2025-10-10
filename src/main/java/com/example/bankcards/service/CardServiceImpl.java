package com.example.bankcards.service;

import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.repository.CardRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CardServiceImpl implements  CardService{

    private final CardRepository cardRepository;

    @Transactional
    @Override
    public CardEntity createCard(CardEntity card) {
        card.setCardStatus(CardStatus.ACTIVE);
        card.setBalance(BigDecimal.ZERO);
        return saveCard(card);
    }

    @Override
    public CardEntity saveCard(CardEntity card) {
        if(cardRepository.existsByCardNumber(card.getCardNumber())) {
            throw  new RuntimeException("Карта с таким номером уже существует!");
        } else {
            return cardRepository.save(card);
        }
    }

    @Override
    public List<CardEntity> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public List<CardEntity> getCardsForUser(Long id) {
        return cardRepository.findAll().stream()
                .filter(card -> card.getUser().getId() == id && card.getUser() != null)
                .toList();
    }

    @Transactional
    @Override
    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }
}
