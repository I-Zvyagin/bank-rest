package com.example.bankcards.util;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserDto toDto(UserEntity entity) {
        if (entity == null) return null;
        return new UserDto(
                entity.getId(),
                entity.getUsername(),
                null,
                entity.getEmail(),
                entity.getRole(),
                entity.getCards()
        );
    }

    public static UserEntity toEntity(UserDto dto) {
        if (dto == null) return null;
        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setCards(dto.getCards());
        return entity;
    }
}
