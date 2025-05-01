package com.example.backgroundmanagementsystem.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 电话
     */
    private String phoneNumber;
    /**
     * 身份证号
     */
    private String idNumber;
    /**
     * 用户名
     */
    private String name;
    /**
     * 个人介绍
     */
    private String description;
    /**
     * 性别，0女 1男
     */
    private Integer gender;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 地址
     */
    private String address;
    /**
     * 微信
     */
    private String weChat;
    /**
     * qq
     */
    private String qq;
    /**
     * 用户状态，0禁用，1启用
     */
    private Integer status;
    /**
     * 用户角色，0用户，1管理员
     */
    private Integer roleType;
}
