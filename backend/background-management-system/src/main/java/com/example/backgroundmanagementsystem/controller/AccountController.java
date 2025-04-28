package com.example.backgroundmanagementsystem.controller;

import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.pojo.dto.UserLoginDTO;
import com.example.backgroundmanagementsystem.pojo.dto.UserTokenDTO;
import com.example.backgroundmanagementsystem.pojo.vo.ResponseVO;
import com.example.backgroundmanagementsystem.service.AccountService;
import com.example.backgroundmanagementsystem.utils.ResponseUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    @PostMapping("/login")
    public ResponseVO<UserTokenDTO> login(@Valid UserLoginDTO userLoginDTO){
        return accountService.login(userLoginDTO);
    }
    /**
     * 获取短信验证码
     * @param phoneNumber
     * @return
     */
    @PostMapping("/getCheckCode")
    public ResponseVO getCheckCode(@NotEmpty String phoneNumber){
        accountService.getCheckCode(phoneNumber);
        return ResponseUtils.success();
    }
    /**
     * 验证码校验
     * @param phoneNumber
     * @param checkCode
     * @return
     */
    @PostMapping("/submitCheckCode")
    public ResponseVO submitCheckCode(@NotEmpty String phoneNumber,@NotEmpty String checkCode){
        accountService.submitCheckCode(phoneNumber,checkCode);
        return ResponseUtils.success();
    }

    /**
     * 忘记密码并修改密码
     * @param phoneNumber
     * @param newPassword
     * @return
     */
    @PostMapping("/forgetAndChangePassword")
    public ResponseVO forgetAndChangePassword(@NotEmpty String phoneNumber,@NotEmpty String newPassword){
        accountService.forgetAndChangePassword(phoneNumber,newPassword);
        return ResponseUtils.success();
    }
}
