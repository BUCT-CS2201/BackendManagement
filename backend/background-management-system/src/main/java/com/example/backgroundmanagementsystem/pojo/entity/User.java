package com.example.backgroundmanagementsystem.pojo.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 电话
     */
    @NotEmpty
    private String phoneNumber;
    /**
     * 密码
     */
    private String password;
    /**
     * 身份证号
     */
    @NotEmpty
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
     * 账户状态，0禁用，1启用
     */
    @NotNull
    private Integer accountStatus;
    /**
     * 评论状态，0禁止评论，1允许评论
     */
    @NotNull
    private Integer commentStatus;
    /**
     * 用户角色，0用户，1管理员
     */
    @NotNull
    private Integer roleType;
}
