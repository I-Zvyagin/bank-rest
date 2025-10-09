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
    public UserEntity saveUser(UserEntity user){
        return userRepository.save(user);
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует!");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует!");
        }

        return saveUser(user);
    }

    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь с таким именем не найден!"));
    }

    //Получение пользователя по имени для Spring Security
    @Override
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    //Получение текущего пользователя
    public UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    @Deprecated
    @Override
    public void getAdmin() {
        UserEntity user = getCurrentUser();
        user.setRole(RoleName.ADMIN);
        saveUser(user);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser (Long id) {
        userRepository.deleteById(id);
    }
}
