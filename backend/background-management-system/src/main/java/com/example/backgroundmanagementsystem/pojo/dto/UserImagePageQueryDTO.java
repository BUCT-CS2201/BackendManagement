package com.example.backgroundmanagementsystem.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户上传图片分页查询DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserImagePageQueryDTO extends PageDTO{
    /**
     * 图片id
     */
    private Long imageId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 关联的评论id
     */
    private Long commentId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 上传时间起始
     */
    private LocalDateTime uploadTimeStart;
    /**
     * 上传时间结束
     */
    private LocalDateTime uploadTimeEnd;
}
