package com.luren.usercenterapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luren.usercenterapi.common.BaseResponse;
import com.luren.usercenterapi.mode.User;
import com.luren.usercenterapi.mode.dto.LoginRequest;
import com.luren.usercenterapi.mode.dto.LoginResponse;
import com.luren.usercenterapi.util.ResultUtils;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {
    
    /**
     * 用户登录
     * @param loginRequest 登录请求
     * @return 登录结果
     */
    BaseResponse<LoginResponse> login(LoginRequest loginRequest);
    
    /**
     * 用户注册
     * @param user 用户信息
     * @return 注册结果
     */
    BaseResponse<User> register(User user);
    
    /**
     * 刷新Token
     * @param refreshToken 刷新Token
     * @return 新的Token信息
     */
    BaseResponse<LoginResponse> refreshToken(String refreshToken);
    
    /**
     * 退出登录
     * @param token 认证Token
     * @return 退出结果
     */
    BaseResponse<?> logout(String token);
    
    /**
     * 根据用户ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    BaseResponse<User> getUserById(int id);
    
    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    BaseResponse<User> getUserByUsername(String username);
    
    /**
     * 获取用户角色
     * @param userId 用户ID
     * @return 角色代码列表
     */
    BaseResponse<List<String>> getUserRoles(int userId);
    
    /**
     * 修改密码
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改结果
     */
    BaseResponse<?> updatePassword(int userId, String oldPassword, String newPassword);
} 