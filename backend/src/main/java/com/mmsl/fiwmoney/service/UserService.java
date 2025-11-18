package com.mmsl.fiwmoney.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mmsl.fiwmoney.model.User;
import com.mmsl.fiwmoney.model.Wallet;
import com.mmsl.fiwmoney.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


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

        return Optional.of(userRepository.save(user));

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
