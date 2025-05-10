package com.example.backgroundmanagementsystem.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户上传图片
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserImage extends BaseEntity{
    /**
     * 图片id
     */
    private Long imageId;
    /**
     * 图片后缀
     */
    private String imageSuffix;
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
}
