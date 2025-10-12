package com.example.bankcards.util;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.CardEntity;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {

    public static CardDto toDto(CardEntity entity) {
        if (entity == null) return null;
        return new CardDto(
                entity.getId(),
                entity.getCardNumber(),
                entity.getBalance(),
                entity.getCardStatus(),
                entity.getExpirationDate()
        );
    }

    public static CardEntity toEntity(CardDto dto) {
        if (dto == null) return null;
        CardEntity entity = new CardEntity();
        entity.setId(dto.getId());
        entity.setCardNumber(dto.getCardNumber());
        entity.setBalance(dto.getBalance());
        entity.setCardStatus(dto.getCardStatus());
        entity.setExpirationDate(dto.getExpirationDate());
        return  entity;
    }
}
