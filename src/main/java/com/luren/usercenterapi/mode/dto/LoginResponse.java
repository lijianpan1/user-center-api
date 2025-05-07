package com.luren.usercenterapi.mode.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 登录响应DTO
 */
@Data
public class LoginResponse implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String token;
    private String refreshToken;
    private Long expireTime;
    private int userId;
    private String username;
    private String nickName;
    private List<String> roles;
} 