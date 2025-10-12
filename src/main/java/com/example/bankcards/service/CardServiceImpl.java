package com.example.bankcards.service;

import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.exception.CardAlreadyExistsException;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.repository.CardRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CardServiceImpl implements  CardService{

    private final CardRepository cardRepository;

    public Optional<CardEntity> getCardById(Long id){
        return cardRepository.findById(id);
    }

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
            throw new CardAlreadyExistsException(card.getCardNumber());
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

    @Override
    public Page<CardEntity> getCardsForUser(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cardRepository.findAllByUser_Id(userId, pageable);
    }

    @Transactional
    @Override
    public void deleteCard(Long id) {
        if(!cardRepository.existsById(id)) {
            throw new CardNotFoundException(id);
        } else {
            cardRepository.deleteById(id);
        }
    }
}
