package com.example.bankcards.service;

import com.example.bankcards.entity.UserEntity;

public interface UserService {
    UserEntity saveUser(UserEntity user);

    UserEntity createUser(UserEntity user);
}
