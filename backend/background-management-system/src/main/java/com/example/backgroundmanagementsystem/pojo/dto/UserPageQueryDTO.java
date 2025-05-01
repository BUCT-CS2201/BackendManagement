package com.example.backgroundmanagementsystem.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPageQueryDTO extends PageDTO{
    private Long userId;
    private String phoneNumber;
    private String idNumber;
    private String name;
    private Integer status;
    private Integer roleType;
}
