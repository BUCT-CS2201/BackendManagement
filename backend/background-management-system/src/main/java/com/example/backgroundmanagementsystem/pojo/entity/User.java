package com.example.backgroundmanagementsystem.pojo.entity;

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
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 电话
     */
    private String phoneNumber;
    /**
     * 用户状态，0禁用，1启用
     */
    private Integer status;
    /**
     * 用户角色，0用户，1管理员
     */
    private Integer roleType;
}
