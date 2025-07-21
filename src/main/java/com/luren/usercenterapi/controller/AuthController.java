package com.luren.usercenterapi.controller;

import com.luren.usercenterapi.annotation.AuthCheck;
import com.luren.usercenterapi.common.BaseResponse;
import com.luren.usercenterapi.mode.User;
import com.luren.usercenterapi.service.UserService;
import com.luren.usercenterapi.mode.dto.LoginRequest;
import com.luren.usercenterapi.mode.dto.LoginResponse;
import com.luren.usercenterapi.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@Api(tags = "认证控制器")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    
    /**
     * 用户登录
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    @AuthCheck( mustRole = "user")
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }
    
    /**
     * 用户注册
     */
    @PostMapping("/register")
    public BaseResponse<User> register(@RequestBody User user) {
        return userService.register(user);
    }
    
    /**
     * 刷新Token
     */
    @PostMapping("/refresh-token")
    public BaseResponse<LoginResponse> refreshToken(@RequestParam String refreshToken) {
        return userService.refreshToken(refreshToken);
    }
    
    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public BaseResponse<?> logout(@RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7);
        return userService.logout(token);
    }
} 