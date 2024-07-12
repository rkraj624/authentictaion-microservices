package com.example.springsecurity.services;

import com.example.springsecurity.entity.User;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.security.SecurityConfig;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }
}
