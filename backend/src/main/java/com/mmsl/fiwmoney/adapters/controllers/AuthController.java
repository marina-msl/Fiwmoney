package com.mmsl.fiwmoney.adapters.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmsl.fiwmoney.adapters.security.JwtUtil;
import com.mmsl.fiwmoney.application.service.UserService;
import com.mmsl.fiwmoney.dto.AuthResponse;
import com.mmsl.fiwmoney.dto.UserRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRequest request) {

        var registeredUser = userService.registerUser(request.username(), request.password(),
                request.name(), request.email());

        if ((registeredUser).isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        String token = jwtUtil.generateToken(request.username());

        return ResponseEntity.status(HttpStatus.CREATED).header("Authorization", "Bearer " + token).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest user) { 
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

        return ResponseEntity.ok(new AuthResponse(token, userFound.get().getWallet().getId(), userFound.get().getUsername()));
    }
}
