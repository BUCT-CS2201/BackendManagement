package com.example.backgroundmanagementsystem.service.impl;

import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.enums.ReviewStatusEnum;
import com.example.backgroundmanagementsystem.exceptions.BaseException;
import com.example.backgroundmanagementsystem.mapper.CommentMapper;
import com.example.backgroundmanagementsystem.pojo.dto.CommentPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Comment;
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
     * @param commentId
     * @param status
     * @param parentId
     */
    @Override
    @Transactional
    public void updateCommentStatus(Long commentId, Integer status,Long parentId) {
        log.info("修改评论状态：commentId={}，status={}，parentId={}",commentId,status,parentId);
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
