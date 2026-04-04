package com.mmsl.fiwmoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mmsl.fiwmoney.adapters.repositories.user.UserJPARepository;
import com.mmsl.fiwmoney.domain.entities.User;
import com.mmsl.fiwmoney.infrastructure.JwtUtil;

@Service
public class AuthService {
 
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserJPARepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(String username, String password) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        return jwtUtil.generateToken(user.getUsername());
    }
}