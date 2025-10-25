package com.mmsl.fiwmoney.controller;

import java.util.Map;

import org.apache.el.parser.TokenMgrError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mmsl.fiwmoney.dto.UserDto;
import com.mmsl.fiwmoney.service.UserService;
import com.mmsl.fiwmoney.util.JwtUtil;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto user) {
        if (userExists(user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        } else {
            String token = jwtUtil.generateToken(user.getUsername(), user.getTenantId());
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token", token, 
                        "username", user.getUsername()));
        }
    }

    private boolean userExists(UserDto user) {
        String username = user.getUsername();
        String psswd = user.getPassword();
        String name = user.getName();   
        String email = user.getEmail();
        String tenantId = user.getTenantId();

        return userService.registerUser(username, psswd, name, email, tenantId).isPresent();
    }
    
}
