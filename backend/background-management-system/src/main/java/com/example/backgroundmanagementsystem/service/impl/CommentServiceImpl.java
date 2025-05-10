package com.example.backgroundmanagementsystem.service.impl;

import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.enums.ReviewStatusEnum;
import com.example.backgroundmanagementsystem.enums.UserCommentStatusEnum;
import com.example.backgroundmanagementsystem.exceptions.BaseException;
import com.example.backgroundmanagementsystem.mapper.CommentMapper;
import com.example.backgroundmanagementsystem.mapper.UserMapper;
import com.example.backgroundmanagementsystem.pojo.dto.CommentPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.dto.CommentStatusUpdateDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Comment;
import com.example.backgroundmanagementsystem.pojo.entity.User;
import com.example.backgroundmanagementsystem.pojo.vo.CommentVO;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.service.CommentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;

    /**
     * 加载评论列表
     * @param commentPageQueryDTO
     * @return
     */
    @Override
    public PageResultVO loadCommentList(CommentPageQueryDTO commentPageQueryDTO) {
        log.info("加载评论列表：{}",commentPageQueryDTO);
        PageHelper.startPage(commentPageQueryDTO.getPageNo(),commentPageQueryDTO.getPageSize());
        Page<CommentVO> page = commentMapper.findBatch(commentPageQueryDTO);
        return new PageResultVO(commentPageQueryDTO.getPageNo(),commentPageQueryDTO.getPageSize(),page.getTotal(),page.getResult());
    }

    /**
     * 修改评论状态
     * @param commentStatusUpdateDTO
     */
    @Override
    @Transactional
    public void updateCommentStatus(CommentStatusUpdateDTO commentStatusUpdateDTO) {
        log.info("修改评论状态：{}",commentStatusUpdateDTO);
        Long commentId = commentStatusUpdateDTO.getCommentId();
        Long parentId = commentStatusUpdateDTO.getParentId();
        Long userId = commentStatusUpdateDTO.getUserId();
        Integer status = commentStatusUpdateDTO.getStatus();
        // 状态校验
        if(!ArrayUtils.contains(
                new int[]{ReviewStatusEnum.PENDING.getStatus(), ReviewStatusEnum.APPROVED.getStatus(), ReviewStatusEnum.REJECTED.getStatus()}, status)){
            throw new BaseException(ResponseCodeEnum.CODE_600);
        }
        // 审核子评论，必须先父评论过审
        if(parentId!=null){
            // 父评论
            Comment parentComment = commentMapper.findByCommentId(parentId);
            if(!ReviewStatusEnum.APPROVED.getStatus().equals(parentComment.getStatus())
                && ReviewStatusEnum.APPROVED.getStatus().equals(status)
            ){
                throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"父评论未过审");
            }
        }
        // 过审评论，用户必须处于可以评论的状态
        User user = userMapper.findByUserId(userId);
        if(ReviewStatusEnum.APPROVED.getStatus().equals(status) && UserCommentStatusEnum.DISABLE.getStatus().equals(user.getCommentStatus())){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"该用户已被禁止评论");
        }
        // 修改评论审核状态
        Comment comment = new Comment();
        comment.setCommentId(commentId);
        comment.setStatus(status);
        commentMapper.updateStatusByCommentId(comment);
        // 父评论状态修改，子评论同态
        comment.setParentId(commentId);
        commentMapper.updateStatusByParentId(comment);
    }
}
