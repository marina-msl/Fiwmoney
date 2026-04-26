package com.mmsl.fiwmoney.adapters.in.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmsl.fiwmoney.adapters.in.security.JwtUtil;
import com.mmsl.fiwmoney.application.service.UserService;
import com.mmsl.fiwmoney.dto.AuthResponse;
import com.mmsl.fiwmoney.dto.UserRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRequest request) {

        var registeredUser = userService.registerUser(request.username(), request.password(),
                request.name(), request.email());

        if ((registeredUser).isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        String token = jwtUtil.generateToken(request.username());

        return ResponseEntity.status(HttpStatus.CREATED).header("Authorization", 
                                                                "Bearer " + token).build();
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
