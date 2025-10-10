package com.example.bankcards.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cards")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "card_id")
    private long id;

    @Column(name = "card_number", nullable = false, unique = true)
    @Size(min = 16, max = 16)
    private String cardNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_status", nullable = false)
    private CardStatus cardStatus;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;
}