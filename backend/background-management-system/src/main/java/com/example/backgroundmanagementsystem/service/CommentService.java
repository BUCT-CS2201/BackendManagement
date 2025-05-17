package com.example.backgroundmanagementsystem.service;

import com.example.backgroundmanagementsystem.pojo.dto.CommentPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.dto.CommentStatusUpdateDTO;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;

public interface CommentService {
    /**
     * 加载评论列表
     * @param commentPageQueryDTO
     * @return
     */
    PageResultVO loadCommentList(CommentPageQueryDTO commentPageQueryDTO);

    /**
     * 修改评论状态
     * @param commentStatusUpdateDTO
     */
    void updateCommentStatus(CommentStatusUpdateDTO commentStatusUpdateDTO);

    /**
     * 删除评论
     * @param commentId
     */
    void deleteComment(Long commentId);
}
