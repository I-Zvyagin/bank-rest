package com.example.bankcards.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CardNumberMaskTest {
    @Test
    void shouldReturnCardMask() {
        String cardNumber = "1234567887654321";

        assertEquals("**** **** **** 4321", CardNumberMask.getMaskedCardNumber(cardNumber));
    }
}