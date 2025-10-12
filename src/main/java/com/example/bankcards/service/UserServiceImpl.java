package com.example.bankcards.service;

import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.entity.RoleName;
import com.example.bankcards.entity.UserEntity;
import com.example.bankcards.exception.EmailAlreadyExistsException;
import com.example.bankcards.exception.UserAlreadyExistsException;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public Optional<UserEntity> getUserById(Long id){
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public UserEntity saveUser(UserEntity user){
        return userRepository.save(user);
    }
    @Override
    public UserEntity createUser(UserEntity user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException(user.getUsername());
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }

        return saveUser(user);
    }

    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
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

    @Transactional
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
