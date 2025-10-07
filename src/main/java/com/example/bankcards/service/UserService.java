package com.example.bankcards.service;

import com.example.bankcards.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserEntity saveUser(UserEntity user);

    UserEntity createUser(UserEntity user);

    UserDetailsService userDetailsService();

    void getAdmin();
}
