package com.example.bankcards.controller;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.RoleName;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.service.UserService;
import com.example.bankcards.util.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/me")
    @Operation(description = "Возвращает текущего пользователя")
    public ResponseEntity<UserDto> getCurrentUser() {
        UserEntity currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(UserMapper.toDto(currentUser));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(description = "Возвращает список всех пользователей")
    public ResponseEntity<List<UserDto>> getAllUser() {
        List<UserDto> users = userService.getAllUsers().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/role")
    @Operation(description = "Меняет роль пользователя")
    public ResponseEntity<UserDto> changeUserRole(@PathVariable  Long id, @RequestParam RoleName role) {
        UserEntity user = userService.getAllUsers().stream()
                .filter(userEntity -> userEntity.getId() == id)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setRole(role);
        UserEntity newRoleUser = userService.saveUser(user);
        return  ResponseEntity.ok(UserMapper.toDto(newRoleUser));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(description = "Удаляет пользователя")
    public ResponseEntity<String> deleteUser(@PathVariable  Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Пользователь удалён!");
    }
}
