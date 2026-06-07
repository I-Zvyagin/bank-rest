package com.example.bankcards.service;

import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.exception.EmailAlreadyExistsException;
import com.example.bankcards.exception.UserAlreadyExistsException;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_shouldSaveUser_whenUsernameAndEmailNotExists() {
        UserEntity user = new UserEntity();
        user.setUsername("test");
        user.setEmail("test@mail.com");

        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        UserEntity result = userService.createUser(user);

        assertEquals("test", result.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    void createUser_shouldThrowException_whenUsernameExists() {
        UserEntity user = new UserEntity();
        user.setUsername("test");
        when(userRepository.existsByUsername("test")).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(user));
    }

    @Test
    void createUser_shouldThrowException_whenEmailExists() {
        UserEntity user = new UserEntity();
        user.setUsername("test");
        user.setEmail("test@mail.com");

        when(userRepository.existsByUsername("test")).thenReturn(false);
        when(userRepository.existsByEmail("test@mail.com")).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(user));
    }

    @Test
    void getByUsername_shouldReturnUser_whenExists() {
        UserEntity user = new UserEntity();
        user.setUsername("test");

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));

        UserEntity result = userService.getByUsername("test");

        assertEquals("test", result.getUsername());
    }

    @Test
    void getByUsername_shouldThrowException_whenNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getByUsername("unknown"));
    }

    @Test
    void deleteUser_shouldCallDelete_whenUserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        userService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_shouldThrowException_whenUserNotExists() {
        when(userRepository.existsById(1L)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
    }
}