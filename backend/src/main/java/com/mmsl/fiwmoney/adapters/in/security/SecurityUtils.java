package com.mmsl.fiwmoney.adapters.in.security;

import org.springframework.security.core.context.SecurityContextHolder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityUtils {

     public static String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    
}
