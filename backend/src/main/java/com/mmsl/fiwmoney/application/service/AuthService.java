package com.mmsl.fiwmoney.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mmsl.fiwmoney.adapters.security.JwtUtil;
import com.mmsl.fiwmoney.domain.entities.User;
import com.mmsl.fiwmoney.domain.ports.IUserRepository;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private IUserRepository userRepository;
    private JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager, IUserRepository userRepository, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String login(String username, String password) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return jwtUtil.generateToken(user.getUsername());
    }
}