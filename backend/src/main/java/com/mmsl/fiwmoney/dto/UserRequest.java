package com.mmsl.fiwmoney.dto;

public record  UserRequest (
    String username,
    String password,
    String name,
    String email,
    String tenantId
) {}