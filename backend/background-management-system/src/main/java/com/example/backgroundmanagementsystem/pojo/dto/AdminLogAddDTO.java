package com.example.backgroundmanagementsystem.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLogAddDTO {
    /**
     * 管理员姓名
     */
    private String adminName;
    
    /**
     * 管理员电话号码
     */
    private String phoneNumber;

    /**
     * 管理员密码
     */
    private String password;
    /**
     * 日志内容
     */
    private String operation;
}
