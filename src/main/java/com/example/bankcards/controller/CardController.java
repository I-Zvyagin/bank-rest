package com.example.bankcards.controller;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.UserService;
import com.example.bankcards.util.CardMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/{userId}")
    @Operation(description = "Создание новой карты")
    public ResponseEntity<CardDto> createCard(
            @PathVariable Long userId,
            @RequestBody @Valid CardDto dto) {
        UserEntity user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));
        CardEntity card = CardMapper.toEntity(dto);
        card.setUser(user);
        CardEntity createdCard = cardService.createCard(card);
        return ResponseEntity.status(HttpStatus.CREATED).body(CardMapper.toDto(createdCard));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(description = "Возвращает список всех карт")
    public ResponseEntity<List<CardDto>> getAllCards() {
        List<CardDto> cards = cardService.getAllCards().stream()
                .map(CardMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cards);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{card_id}/status")
    @Operation(description = "Меняет статус карты")
    public ResponseEntity<CardDto> changeCardStatus(@PathVariable Long card_id, @RequestParam CardStatus status) {
        CardEntity card = cardService.getCardById(card_id)
                .orElseThrow(() -> new RuntimeException("Карта не найдена!"));
        card.setCardStatus(status);
        CardEntity updatedCard = cardService.saveCard(card);
        return ResponseEntity.ok(CardMapper.toDto(updatedCard));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(description = "Удаляет карту")
    public ResponseEntity<String> deleteCard(@PathVariable  Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.ok("Карта удалена");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my_cards")
    @Operation(description = "Возвращает список карт текущего пользователя")
    public ResponseEntity<Page<CardDto>> getMyCards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        UserEntity currentUser = userService.getCurrentUser();
        Page<CardEntity> cardsPage = cardService.getCardsForUser(currentUser.getId(), page, size);
        Page<CardDto> dtoPage = cardsPage.map(CardMapper::toDto);
        return ResponseEntity.ok(dtoPage);
    }
}
