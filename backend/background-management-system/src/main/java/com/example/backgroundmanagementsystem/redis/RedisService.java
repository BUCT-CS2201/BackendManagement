package com.example.backgroundmanagementsystem.redis;

import org.springframework.stereotype.Service;

@Service
public class RedisService {
    /**
     * 获取验证码
     * @param phoneNumber
     * @return
     */
    public String getCheckCode(String phoneNumber) {
        return null;
    }

    /**
     * 清除验证码
     * @param phoneNumber
     */
    public void removeCheckCode(String phoneNumber){}

}
