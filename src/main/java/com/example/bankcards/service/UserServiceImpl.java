package com.example.bankcards.service;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.RoleName;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public UserDto saveUser(UserDto user){
        UserEntity userRequestingSave = UserMapper.toEntity(user);
        UserEntity savedUser = userRepository.save(userRequestingSave);
        return UserMapper.toDto(savedUser);
    }

    @Override
    public UserDto createUser(UserDto user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует!");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует!");
        }

        return saveUser(user);
    }

    public UserDto getByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь с таким именем не найден!"));
        return UserMapper.toDto(userEntity);
    }

    //Получение пользователя по имени для Spring Security
    @Override
    public UserDetailsService userDetailsService() {
        return username -> UserMapper.toEntity(this.getByUsername(username));
    }

    //Получение текущего пользователя
    public UserDto getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    @Deprecated
    @Override
    public void getAdmin() {
        UserDto user = getCurrentUser();
        user.setRole(RoleName.ADMIN);
        saveUser(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
}
