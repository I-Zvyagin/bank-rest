package com.example.bankcards.dto;

import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.entity.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    private long id;

    private String username;

    private String password;

    private String email;

    private RoleName role;

    private List<CardEntity> cards;
}
