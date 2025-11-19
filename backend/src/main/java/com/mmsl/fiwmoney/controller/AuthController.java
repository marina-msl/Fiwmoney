package com.mmsl.fiwmoney.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmsl.fiwmoney.dto.AuthResponse;
import com.mmsl.fiwmoney.dto.UserDto;
import com.mmsl.fiwmoney.infrastructure.JwtUtil;
import com.mmsl.fiwmoney.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto user) {

       var registeredUser = userService.registerUser(user.username(), user.password(),
                user.name(), user.email());
        
        if (registeredUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Username already exists"));
        }

        String token = jwtUtil.generateToken(user.username());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(token, user.username()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto user) { 
        var userFound = userService.findByUsername(user.username());
        
        if (userFound.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }

        if (!userService.checkPassword(userFound.get(), user.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }

        String token = jwtUtil.generateToken(userFound.get().getUsername());

        return ResponseEntity.ok(new AuthResponse(token, userFound.get().getUsername()));
    }
}
