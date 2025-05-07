package com.luren.usercenterapi.controller;

import com.luren.usercenterapi.common.BaseResponse;
import com.luren.usercenterapi.mode.User;
import com.luren.usercenterapi.service.UserService;
import com.luren.usercenterapi.util.JwtUtil;
import com.luren.usercenterapi.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public BaseResponse<User> getCurrentUser(@RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7);
        int userId = jwtUtil.getUserIdFromToken(token);
        return userService.getUserById(userId);
    }
    
    /**
     * 获取用户角色
     */
    @GetMapping("/roles")
    public BaseResponse<List<String>> getUserRoles(@RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7);
        int userId = jwtUtil.getUserIdFromToken(token);
        return userService.getUserRoles(userId);
    }
    
    /**
     * 修改密码
     */
    @PostMapping("/update-password")
    public BaseResponse<?> updatePassword(@RequestHeader("Authorization") String authorization,
                                         @RequestParam String oldPassword,
                                         @RequestParam String newPassword) {
        String token = authorization.substring(7);
        Integer userId = jwtUtil.getUserIdFromToken(token);
        return userService.updatePassword(userId, oldPassword, newPassword);
    }
    
    /**
     * 获取用户列表（需要管理员权限）
     */
    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BaseResponse<List<User>> getUserList() {
        // 这里简化处理，实际应该从数据库查询
        return ResultUtils.error("功能未实现");
    }

    /**
     * 获取用户列表（需要管理员权限）
     */
    @GetMapping("/userByUsername")
    public BaseResponse<User> getUserByUsername() {
        return userService.getUserByUsername("luren");
    }
} 