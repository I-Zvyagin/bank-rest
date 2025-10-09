package com.example.bankcards.service;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserEntity saveUser(UserEntity user);

    UserEntity createUser(UserEntity user);

    UserDetailsService userDetailsService();

    void getAdmin();

    List<UserEntity> getAllUsers();

    void deleteUser(Long id);
}
