package com.example.bankcards.util;

public class CardNumberMask {

    public static String getMaskedCardNumber(String cardNumber) {
        String open = cardNumber.substring(11);
        String close = "**** ".repeat(3);
        return close + open;
    }

}
