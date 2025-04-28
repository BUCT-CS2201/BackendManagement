package com.example.backgroundmanagementsystem.pojo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户密码修改
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordChangeDTO {
    @NotNull
    private Long userId;
    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String newPassword;
}
