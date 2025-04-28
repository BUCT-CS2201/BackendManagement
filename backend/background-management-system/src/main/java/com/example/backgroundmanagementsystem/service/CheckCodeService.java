package com.example.backgroundmanagementsystem.service;

/**
 * 验证码接口层
 */
public interface CheckCodeService {

    /**
     * 生成验证码
     */
    void generateCheckCode(String phoneNumber);

    void validateCheckCode(String phoneNumber,String checkCode);
}
