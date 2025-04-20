package com.example.backgroundmanagementsystem.pojo.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    @NotEmpty
    String phoneNumber;
    @NotEmpty
    String password;
}
