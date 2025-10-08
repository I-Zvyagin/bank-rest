package com.example.bankcards.service;

import com.example.bankcards.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDto saveUser(UserDto user);

    UserDto createUser(UserDto user);

    UserDetailsService userDetailsService();

    void getAdmin();

    List<UserDto> getAllUsers();
}
