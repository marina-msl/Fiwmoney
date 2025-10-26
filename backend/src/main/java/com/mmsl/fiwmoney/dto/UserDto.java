package com.mmsl.fiwmoney.dto;

public record  UserDto (
    String username,
    String password,
    String name,
    String email,
    String tenantId
) {}