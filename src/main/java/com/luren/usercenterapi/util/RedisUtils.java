package com.luren.usercenterapi.util;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RedisUtils
 * @Description: TODO
 * @Author: lijianpan
 * @CreateTime: 2025-05-15  16:01
 * @Version: 1.0
 */
@Component
@AllArgsConstructor
public class RedisUtils {
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate<String, Object> redisTemplate(){
        return redisTemplate;
    }

    public void setToken(String key, Object value) {
        set("token:" + key, value, jwtUtil.getExpiration());
    }

    public void setRefreshToken(String key, Object value) {
        set("refreshToken:" + key, value, jwtUtil.getRefreshExpiration());
    }

    public Object getToken(String key) {
        return get("token:" + key);
    }

    public Object getRefreshToken(String key) {
        return get("refreshToken:" + key);
    }

    public void deleteToken(String key) {
        delete("token:" + key);
    }

    public void deleteRefreshToken(String key) {
        delete("refreshToken:" + key);
    }

    // ============== common operations ==============

    /**
     * 设置缓存
     * @param key 键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置缓存并指定过期时间
     * @param key 键
     * @param value 值
     * @param time 过期时间（秒）
     */
    public void set(String key, Object value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存
     * @param key 键
     */
    public void delete(String key) {
        if (key != null) {
            redisTemplate.delete(key);
        }
    }

    // ============== hash operations ==============

    /**
     * 向Hash中添加数据
     * @param key 键
     * @param hashKey hash键
     * @param value 值
     */
    public void hset(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 获取Hash中的数据
     * @param key 键
     * @param hashKey hash键
     * @return 值
     */
    public Object hget(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 获取整个Hash
     * @param key 键
     * @return Map
     */
    public Map<Object, Object> hgetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    // ============== list operations ==============

    /**
     * 向List中添加数据
     * @param key 键
     * @param value 值
     */
    public void lpush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 获取List中的数据
     * @param key 键
     * @param start 开始位置
     * @param end 结束位置
     * @return List
     */
    public List<Object> lrange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    // ============== set operations ==============

    /**
     * 向Set中添加数据
     * @param key 键
     * @param value 值
     */
    public void sadd(String key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 获取Set中的数据
     * @param key 键
     * @return Set
     */
    public Set<Object> sGet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    // 判断key是否存在
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }
}