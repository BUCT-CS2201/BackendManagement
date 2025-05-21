package com.example.backgroundmanagementsystem.redis;

import com.example.backgroundmanagementsystem.pojo.dto.UserTokenDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RedisService {

    private static Map<String,UserTokenDTO> userTokenDTOMap = new HashMap<>();
    private static Map<String,String> checkCodeMap = new HashMap<>();

    /**
     * 存储验证码
     * @param phoneNumber
     * @param checkCode
     */
    public void saveCheckCode(String phoneNumber, String checkCode) {
        checkCodeMap.put(phoneNumber,checkCode);
    }

    /**
     * 获取验证码
     * @param phoneNumber
     * @return
     */
    public String getCheckCode(String phoneNumber) {
        return checkCodeMap.get(phoneNumber);
    }

    /**
     * 清除验证码
     * @param phoneNumber
     */
    public void removeCheckCode(String phoneNumber){
        checkCodeMap.remove(phoneNumber);
    }


    /**
     * 清除用户信息缓存
     * @param token
     */
    public void removeUserTokenDTO(String token) {
        userTokenDTOMap.remove(token);
    }

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    public UserTokenDTO getUserTokenDTO(String token) {
        return userTokenDTOMap.getOrDefault(token, null);
    }

    /**
     * 存储用户信息
     * @param userTokenDTO
     */
    public void saveUserTokenDTO(String token,UserTokenDTO userTokenDTO) {
        userTokenDTOMap.put(token,userTokenDTO);
    }
}
