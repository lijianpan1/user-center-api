package com.luren.usercenterapi.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public class MD5Utils {

    /**
     * MD5加密
     * @param str
     * @return 字符串密文
     */
    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    /**
     * MD5加盐加密
     * @param str
     * @return 字符串密文
     */
    public static String md5Salt(String str,String salt) {
        return DigestUtils.md5Hex(DigestUtils.md5Hex(str) + salt);
    }
}
