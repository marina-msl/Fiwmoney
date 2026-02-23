package com.mmsl.fiwmoney.dto;

public record AuthResponse(
        String token,
        Long walletId, String username
) {}
