package com.luren.usercenterapi.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存工具类
 *
 * @author lijianpan
 */
public class LocalCacheUtils {
    private static final Cache<String, String> LOCAL_CACHE = Caffeine.newBuilder()
            // 初始容量
            .initialCapacity(1024)
            // 最大容量
            .maximumSize(100_000L)
            // 过期时间
//            .expireAfterWrite(Duration.ofMillis(5))
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    /**
     * 根据键获取缓存中的值
     * @param key 缓存键
     * @return 缓存中对应的值，如果不存在则返回null
     */
    public static String get(String key) {
        return LOCAL_CACHE.getIfPresent(key);
    }

    /**
     * 将键值对放入缓存
     * @param key 缓存键
     * @param value 缓存值
     */
    public static void put(String key, String value) {
        LOCAL_CACHE.put(key, value);
    }

    /**
     * 从缓存中移除指定键的条目
     * @param key 要移除的缓存键
     */
    public static void remove(String key) {
        LOCAL_CACHE.invalidate(key);
    }

    /**
     * 清空所有缓存条目
     */
    public static void clear() {
        LOCAL_CACHE.invalidateAll();
    }

    /**
     * 清理缓存，移除过期条目
     */
    public static void refresh() {
        LOCAL_CACHE.cleanUp();
    }
}
