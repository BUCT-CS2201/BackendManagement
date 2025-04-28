package com.example.backgroundmanagementsystem.service;

import com.example.backgroundmanagementsystem.pojo.dto.UserLoginDTO;
import com.example.backgroundmanagementsystem.pojo.dto.UserTokenDTO;
import com.example.backgroundmanagementsystem.pojo.vo.ResponseVO;

public interface AccountService {
    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    ResponseVO<UserTokenDTO> login(UserLoginDTO userLoginDTO);

    /**
     * 获取验证码
     * @param phoneNumber
     */
    void getCheckCode(String phoneNumber);

    /**
     * 验证码校验
     * @param phoneNumber
     * @param checkCode
     */
    void submitCheckCode(String phoneNumber, String checkCode);

    /**
     * 忘记密码并修改密码
     * @param phoneNumber
     * @param newPassword
     */
    void forgetAndChangePassword(String phoneNumber, String newPassword);
}
