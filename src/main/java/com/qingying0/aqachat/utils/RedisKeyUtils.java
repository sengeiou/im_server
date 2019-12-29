package com.qingying0.aqachat.utils;

/**
 * 生成redisKey的工具类
 */
public class RedisKeyUtils {

    public static final String SPLIT= ":";
    public static final String TOKEN = "token";

    public static String getTokenKey(Long userId) {
        return TOKEN + SPLIT + userId;
    }
}
