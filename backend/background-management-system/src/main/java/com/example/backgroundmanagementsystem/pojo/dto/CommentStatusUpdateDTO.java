package com.example.backgroundmanagementsystem.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 审核评论状态DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentStatusUpdateDTO {
    /**
     * 评论id
     */
    @NotNull
    private Long commentId;
    /**
     * 父评论id
     */
    private Long parentId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 要修改的评论状态
     */
    @NotNull
    private Integer status;
}
