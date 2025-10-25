package com.mmsl.fiwmoney.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mmsl.fiwmoney.model.User;
import com.mmsl.fiwmoney.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Optional<User> registerUser(String username, String password, String name, String email, String tenantId) {
        if (userRepository.existsByUsername(username)) {
           return Optional.empty();
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setEmail(email);
        user.setTenantId(tenantId);

        return Optional.of(userRepository.save(user));
    }
}
