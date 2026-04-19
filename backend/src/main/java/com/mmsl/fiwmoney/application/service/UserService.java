package com.mmsl.fiwmoney.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mmsl.fiwmoney.domain.entities.User;
import com.mmsl.fiwmoney.domain.entities.Wallet;
import com.mmsl.fiwmoney.domain.ports.IUserRepository;

@Service
public class UserService {
    
    private final IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Optional<User> registerUser(String username, String password, String name, String email) {

        if (existsByUsername(username)) {
            return Optional.empty();
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .name(name)
                .email(email)
                .wallet(new Wallet())
                .build();

        userRepository.save(user);
        return Optional.of(user);
    }

    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
