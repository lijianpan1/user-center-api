package com.luren.usercenterapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luren.usercenterapi.common.BaseResponse;
import com.luren.usercenterapi.common.ErrorCode;
import com.luren.usercenterapi.exception.CustomException;
import com.luren.usercenterapi.mapper.RoleMapper;
import com.luren.usercenterapi.mapper.UserMapper;
import com.luren.usercenterapi.mapper.UserRoleMapper;
import com.luren.usercenterapi.mode.Role;
import com.luren.usercenterapi.mode.User;
import com.luren.usercenterapi.mode.UserRole;
import com.luren.usercenterapi.mode.dto.LoginRequest;
import com.luren.usercenterapi.mode.dto.LoginResponse;
import com.luren.usercenterapi.service.RoleService;
import com.luren.usercenterapi.service.UserRoleService;
import com.luren.usercenterapi.service.UserService;
import com.luren.usercenterapi.util.JwtUtil;
import com.luren.usercenterapi.util.RedisUtils;
import com.luren.usercenterapi.util.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 *
 * @author lijianpan
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Override
    public BaseResponse<LoginResponse> login(LoginRequest loginRequest) {
        // 验证用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", loginRequest.getUsername());
        User user = this.getOne(queryWrapper);

        if(user==null){
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }

        if (user.getStatus() == 1) {
            throw new CustomException(ErrorCode.ACCOUNT_HAS_BEEN_DISABLED);
        }
        
        // 验证密码
        if (passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())) {
            throw new CustomException(ErrorCode.USER_PASSWORD_ERROR);
        }
        
        // 3. 生成Token
        String token = jwtUtil.generateToken(user.getUsername(), user.getId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername(), user.getId());
        
        // 4. 更新用户最后登录时间
        user.setLastLoginTime(new Date());
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set("last_login_time",new Date());
        updateWrapper.eq("username", loginRequest.getUsername());
        this.update(updateWrapper);
        
        // 5. 将Token存入Redis
        redisUtils.setToken(token, user.getId());
        redisUtils.setRefreshToken(refreshToken, user.getId());
        
        // 6. 构建登录响应
        LoginResponse loginResponse = encapsulateLoginResponse(user,token,refreshToken);
        
        return ResultUtils.ok("登录成功", loginResponse);
    }

    @Override
    public BaseResponse<User> register(User user) {
        boolean result = StringUtils.isAnyEmpty(user.getUsername(),user.getPassword(),user.getCheckPassword());

        if(result){
            throw new CustomException(ErrorCode.PARAMS_ERROR,"请输入完整信息");
        }

        // 1. 检查用户名是否已存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        User existUser = this.getOne(queryWrapper);
        if(existUser!=null){
            throw new CustomException(ErrorCode.USER_EXIST);
        }

        // 2. 设置用户信息
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(0);

        // 3. 保存用户
        this.save(user);
        
        // 4. 返回结果，脱敏
        user.setPassword(null);
        user.setCheckPassword(null);
        return ResultUtils.ok("注册成功", user);
    }

    @Override
    public BaseResponse<LoginResponse> refreshToken(String refreshToken) {
        // 1. 验证刷新Token是否有效
        if (jwtUtil.isTokenExpired(refreshToken)) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_HAS_EXPIRED);
        }
        
        // 2. 从Redis中获取用户ID
        Long userId = (Long) redisUtils.getRefreshToken(refreshToken);
        if (userId == null) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_IS_INVALID);
        }
        
        // 3. 获取用户信息
        User user = this.getById(userId);
        
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }
        
        // 4. 生成新的Token
        String newToken = jwtUtil.generateToken(user.getUsername(), user.getId());
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getUsername(), user.getId());
        
        // 5. 删除旧的Token
        redisUtils.deleteRefreshToken(refreshToken);
        
        // 6. 将新Token存入Redis
        redisUtils.setToken(newToken, user.getId());
        redisUtils.setRefreshToken(newRefreshToken, user.getId());
        
        // 7. 构建响应
        LoginResponse loginResponse = encapsulateLoginResponse(user,newToken,newRefreshToken);
        return ResultUtils.ok("刷新Token成功", loginResponse);
    }

    @Override
    public BaseResponse<?> logout(String token) {
        // 1. 从Redis中删除Token
        redisUtils.deleteToken(token);
        return ResultUtils.ok("退出登录成功");
    }

    @Override
    public BaseResponse<User> getUserById(Integer id) {
        // 查找用户
        User user = this.getById(id);
        
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }
        
        // 不返回密码
        return ResultUtils.ok(encapsulateAndDesensitizeUserInformation(user));
    }

    @Override
    public BaseResponse<User> getUserByUsername(String username) {
        // 查找用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = this.getOne(queryWrapper);
        
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }

        // 不返回密码
        return ResultUtils.ok(encapsulateAndDesensitizeUserInformation(user));
    }

    @Override
    public List<String> getUserRoles(Integer userId) {

        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        // 模拟获取用户角色
        List<UserRole> userRoles = userRoleService.list(queryWrapper);

        //获取角色id并添加到集合中
        List<String> roleIds = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            roleIds.add(String.valueOf(userRole.getRoleId()));
        }

        List<Role> roles = !roleIds.isEmpty() ? roleService.listByIds(roleIds):new ArrayList<>();

        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }

    @Override
    public BaseResponse<String> updatePassword(Integer userId, String oldPassword, String newPassword) {
        // 查找用户
        User user = this.getById(userId);
        
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }
        
        // 验证旧密码
        if (passwordEncoder.matches(oldPassword,user.getPassword())) {
            return ResultUtils.error("旧密码错误");
        }
        
        // 更新密码
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedDate(new Date());
        
        return ResultUtils.ok("密码修改成功");
    }

    @Override
    public BaseResponse<String> restoreDeletedData(Integer id) {
        int count = this.baseMapper.restoreDeletedData(id);
        if (count==0){
            return ResultUtils.error("恢复数据失败");
        }
        return ResultUtils.ok("恢复数据成功");
    }

    private User encapsulateAndDesensitizeUserInformation(User user){
        User result = new User();
        result.setId(user.getId());
        result.setUsername(user.getUsername());
        result.setEmail(user.getEmail());
        result.setPhone(user.getPhone());
        result.setNickname(user.getNickname());
        result.setStatus(user.getStatus());
        result.setCreatedDate(user.getCreatedDate());
        result.setUpdatedDate(user.getUpdatedDate());
        result.setLastLoginTime(user.getLastLoginTime());

        return result;
    }

    private LoginResponse encapsulateLoginResponse(User user,String token,String refreshToken) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setRefreshToken(refreshToken);
        loginResponse.setExpireTime(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        loginResponse.setUserId(user.getId());
        loginResponse.setUsername(user.getUsername());
        loginResponse.setNickName(user.getNickname());

        // 获取用户角色
        loginResponse.setRoles(getUserRoles(user.getId()));

        return loginResponse;
    }
} 