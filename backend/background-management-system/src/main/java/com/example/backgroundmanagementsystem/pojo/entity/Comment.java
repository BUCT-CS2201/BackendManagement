package com.example.backgroundmanagementsystem.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity{
    /**
     * 评论id
     */
    private Long commentId;
    private Long relicId;
    private Long userId;
    private String content;
    /**
     * 父评论id
     */
    private Long parentId;
    private Integer likeCount;
    private Integer replyCount;
    /**
     * 审核状态，0审核中，1过审，2未过审
     */
    private Integer status;
}
