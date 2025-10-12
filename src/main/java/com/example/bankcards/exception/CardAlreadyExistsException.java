package com.example.bankcards.exception;

public class CardAlreadyExistsException extends RuntimeException {

    public CardAlreadyExistsException(String cardNumber) {
        super("Карта с номером " + cardNumber + " уже существует");
    }
}
