package com.example.bankcards.controller;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.RoleName;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.service.UserService;
import com.example.bankcards.util.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @Operation(description = "Регистрация нового пользователя")
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserDto userDto) {
        UserEntity user = UserMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(RoleName.USER);
        UserEntity createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(createdUser));
    }
}
