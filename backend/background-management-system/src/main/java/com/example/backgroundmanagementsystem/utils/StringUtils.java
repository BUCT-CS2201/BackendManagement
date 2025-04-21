package com.example.backgroundmanagementsystem.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 生成用户token
     * @param userId
     * @return
     */
    public static String getUserToken(Long userId){
        return DigestUtils.md5Hex(String.valueOf(userId));
    }
}
