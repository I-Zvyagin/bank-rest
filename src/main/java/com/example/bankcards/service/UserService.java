package com.example.bankcards.service;

import com.example.bankcards.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserEntity> getUserById(Long id);

    UserEntity saveUser(UserEntity user);

    UserEntity createUser(UserEntity user);

    UserDetailsService userDetailsService();

    void getAdmin();

    UserEntity getCurrentUser();

    List<UserEntity> getAllUsers();

    void deleteUser(Long id);
}
