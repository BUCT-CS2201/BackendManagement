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
}
