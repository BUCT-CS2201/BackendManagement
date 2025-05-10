package com.example.backgroundmanagementsystem.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图片审核状态修改DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserImageStatusUpdateDTO {
    /**
     * 图片id
     */
    @NotNull
    private Long imageId;
    /**
     * 评论id
     */
    @NotNull
    private Long commentId;
    /**
     * 要修改的状态
     */
    @NotNull
    private Integer status;
}
