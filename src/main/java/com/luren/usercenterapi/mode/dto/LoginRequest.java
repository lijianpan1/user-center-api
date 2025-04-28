package com.luren.usercenterapi.mode.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录请求DTO
 */
@Data
public class LoginRequest implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
} 