package com.luren.usercenterapi.security;

import com.luren.usercenterapi.common.BaseResponse;
import com.luren.usercenterapi.mode.User;
import com.luren.usercenterapi.service.UserService;
import com.luren.usercenterapi.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * JWT用户详情服务
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
    
    @Override
    public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取用户信息
        BaseResponse<User> userResult = userService.getUserByUsername(username);
        if (userResult.getCode() != 200 || userResult.getData() == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        
        User user = userResult.getData();
        
        // 获取用户角色
        List<String> roles = userService.getUserRoles(user.getId());
        
        // 创建UserDetails对象
        return new JwtUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getNickname(),
                user.getStatus(),
                roles
        );
    }
} 