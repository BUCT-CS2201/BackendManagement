package com.example.backgroundmanagementsystem.controller;

import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.pojo.dto.UserLoginDTO;
import com.example.backgroundmanagementsystem.pojo.dto.UserTokenDTO;
import com.example.backgroundmanagementsystem.pojo.vo.ResponseVO;
import com.example.backgroundmanagementsystem.service.AccountService;
import com.example.backgroundmanagementsystem.utils.ResponseUtils;
import jakarta.validation.Valid;
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
}
