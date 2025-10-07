package com.example.bankcards.dto;

import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.util.CardNumberMask;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class CardDto {

    private long id;

    private String cardNumber;

    private String maskedCardNumber;

    private BigDecimal balance;

    private CardStatus cardStatus;

    private Date expirationDate;

    public CardDto(long id, String cardNumber, BigDecimal balance, CardStatus cardStatus, Date expirationDate) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.maskedCardNumber = CardNumberMask.getMaskedCardNumber(cardNumber);
        this.balance = balance;
        this.cardStatus = cardStatus;
        this.expirationDate = expirationDate;
    }
}
