package com.mmsl.fiwmoney.dto;

import lombok.Data;

@Data
public class UserDto {
    
    private String username;
    private String password;
    private String name;
    private String email;
    private String tenantId;

}