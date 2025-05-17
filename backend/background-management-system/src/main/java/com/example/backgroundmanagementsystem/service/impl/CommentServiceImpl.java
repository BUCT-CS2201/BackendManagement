package com.example.backgroundmanagementsystem.service.impl;

import com.example.backgroundmanagementsystem.context.BaseContext;
import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.enums.ReviewStatusEnum;
import com.example.backgroundmanagementsystem.enums.UserCommentStatusEnum;
import com.example.backgroundmanagementsystem.exceptions.BaseException;
import com.example.backgroundmanagementsystem.mapper.AdminLogMapper;
import com.example.backgroundmanagementsystem.mapper.CommentMapper;
import com.example.backgroundmanagementsystem.mapper.ImageMapper;
import com.example.backgroundmanagementsystem.mapper.UserMapper;
import com.example.backgroundmanagementsystem.pojo.dto.CommentPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.dto.CommentStatusUpdateDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Comment;
import com.example.backgroundmanagementsystem.pojo.entity.User;
import com.example.backgroundmanagementsystem.pojo.entity.UserImage;
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
    private final ImageMapper imageMapper;
    private final AdminLogMapper adminLogMapper;
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
        // 过审子评论，必须先过审父评论
        if(parentId!=null){
            // 父评论
            Comment parentComment = commentMapper.findByCommentId(parentId);
            if(parentComment==null){
                throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"该评论所属父评论已被删除");
            }
            if(!ReviewStatusEnum.APPROVED.getStatus().equals(parentComment.getStatus())
                && ReviewStatusEnum.APPROVED.getStatus().equals(status)
            ){
                throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"父评论未过审");
            }
        }
        // 过审评论，用户必须处于可评论的状态
        User user = userMapper.findByUserId(userId);
        if(ReviewStatusEnum.APPROVED.getStatus().equals(status) && UserCommentStatusEnum.DISABLE.getStatus().equals(user.getCommentStatus())){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"该用户已被禁止评论");
        }
        // 修改评论审核状态
        Comment comment = new Comment();
        comment.setCommentId(commentId);
        comment.setStatus(status);
        commentMapper.updateStatusByCommentId(comment);
        adminLogMapper.addLog(BaseContext.getUserToken().getName(), "修改评论状态:"+commentId);
        // 评论状态修改时，关联子评论和图片
        if(!ReviewStatusEnum.APPROVED.getStatus().equals(status)){
            // 父评论由过审改为未过审，则子评论全变为未过审
            comment.setParentId(commentId);
            commentMapper.updateStatusByParentId(comment);
            // 评论由过审改为未过审，则其关联图片全变为未过审
            UserImage userImage = new UserImage();
            userImage.setCommentId(commentId);
            userImage.setStatus(status);
            imageMapper.updateStatusByCommentId(userImage);
        }
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        log.info("删除评论");
        // 删除评论
        commentMapper.deleteByCommentId(commentId);
        // 删除子评论
        commentMapper.deleteByParentId(commentId);
        // 删除图片
        imageMapper.deleteByCommentId(commentId);
        adminLogMapper.addLog(BaseContext.getUserToken().getName(), "删除评论:"+commentId);
    }
}
