package com.luren.usercenterapi.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Duration;

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
            .expireAfterWrite(Duration.ofMillis(5))
            .build();

    public static String get(String key) {
        return LOCAL_CACHE.getIfPresent(key);
    }

    public static void put(String key, String value) {
        LOCAL_CACHE.put(key, value);
    }

    public static void remove(String key) {
        LOCAL_CACHE.invalidate(key);
    }

    public static void clear() {
        LOCAL_CACHE.invalidateAll();
    }

    public static void refresh() {
        LOCAL_CACHE.cleanUp();
    }
}
