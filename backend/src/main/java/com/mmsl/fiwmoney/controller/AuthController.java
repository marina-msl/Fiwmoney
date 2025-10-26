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
import com.mmsl.fiwmoney.service.UserService;
import com.mmsl.fiwmoney.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto user) {

       var registeredUser = userService.registerUser(user.username(), user.password(),
                user.name(), user.email(), user.tenantId());
        
        if (registeredUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Username already exists"));
        }

        String token = jwtUtil.generateToken(user.username(), user.tenantId());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(token, user.username()));
    }
    
}
