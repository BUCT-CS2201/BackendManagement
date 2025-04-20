package com.example.backgroundmanagementsystem.controller;

import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.pojo.vo.ResponseVO;
import com.example.backgroundmanagementsystem.utils.ResponseUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/login")
    public ResponseVO test(String phoneNumber){
        return ResponseUtils.success(ResponseCodeEnum.CODE_200.getMsg(),"电话号码是："+phoneNumber);
    }
}
