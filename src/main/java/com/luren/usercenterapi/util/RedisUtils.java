package com.luren.usercenterapi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RedisUtils
 * @Description: TODO
 * @Author: lijianpan
 * @CreateTime: 2025-05-15  16:01
 * @Version: 1.0
 */
@Component
public class RedisUtils {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate<String, Object> redisTemplate(){
        return redisTemplate;
    }

    public void setToken(String key, Object value) {
        redisTemplate.opsForValue().set("token:" + key, value, jwtUtil.getExpiration(), TimeUnit.SECONDS);
    }

    public void setRefreshToken(String key, Object value) {
        redisTemplate.opsForValue().set("refreshToken:" + key, value, jwtUtil.getRefreshExpiration(), TimeUnit.SECONDS);
    }

    public Object getToken(String key) {
        return redisTemplate.opsForValue().get("token:" + key);
    }

    public Object getRefreshToken(String key) {
        return redisTemplate.opsForValue().get("refreshToken:" + key);
    }

    public void deleteToken(String key) {
        redisTemplate.delete("token:" + key);
    }

    public void deleteRefreshToken(String key) {
        redisTemplate.delete("refreshToken:" + key);
    }
}