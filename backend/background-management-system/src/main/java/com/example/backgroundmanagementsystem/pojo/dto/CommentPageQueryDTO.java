package com.example.backgroundmanagementsystem.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPageQueryDTO extends PageDTO{
    /**
     * 评论id
     */
    private Long commentId;
    private Long relicId;
    private Long userId;
    /**
     * 父评论id
     */
    private Long parentId;
    /**
     * 审核状态，0审核中，1过审，2未过审
     */
    private Integer status;
    /**
     * 评论时间起始
     */
    private LocalDateTime commentTimeStart;
    /**
     * 评论时间结束
     */
    private LocalDateTime commentTimeEnd;
}
