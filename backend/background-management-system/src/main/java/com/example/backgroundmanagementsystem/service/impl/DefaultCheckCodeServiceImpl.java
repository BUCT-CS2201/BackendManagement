package com.example.backgroundmanagementsystem.service.impl;

import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.exceptions.BaseException;
import com.example.backgroundmanagementsystem.redis.RedisService;
import com.example.backgroundmanagementsystem.service.CheckCodeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

/**
 * 验证码接口默认实现类
 */
@Service
@RequiredArgsConstructor
public class DefaultCheckCodeServiceImpl implements CheckCodeService {
    private final RedisService redisService;
    @Override
    public void generateCheckCode(String phoneNumber) {
        // 生成6位验证码
        String checkCode = RandomStringUtils.randomNumeric(6);
        // todo 存入Redis
        System.out.println("验证码："+phoneNumber+":"+checkCode);
        redisService.saveCheckCode(phoneNumber,checkCode);
    }

    @Override
    public void validateCheckCode(String phoneNumber,String checkCode) {
        String correctCheckCode = redisService.getCheckCode(phoneNumber);
        if (correctCheckCode==null || !correctCheckCode.equals(checkCode)){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"验证码错误");
        }
        // 清除redis验证码缓存
        redisService.removeCheckCode(phoneNumber);
    }
}
