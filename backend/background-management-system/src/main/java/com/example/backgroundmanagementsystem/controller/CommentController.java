package com.example.backgroundmanagementsystem.controller;

import com.example.backgroundmanagementsystem.annotations.PageQueryAutoFill;
import com.example.backgroundmanagementsystem.pojo.dto.CommentPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.dto.CommentStatusUpdateDTO;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.pojo.vo.ResponseVO;
import com.example.backgroundmanagementsystem.service.CommentService;
import com.example.backgroundmanagementsystem.utils.ResponseUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final CommentService commentService;

    /**
     * 加载评论列表
     * @param commentPageQueryDTO
     * @return
     */
    @PostMapping("/loadCommentList")
    @PageQueryAutoFill
    public ResponseVO<PageResultVO> loadCommentList(CommentPageQueryDTO commentPageQueryDTO){
        return ResponseUtils.success(commentService.loadCommentList(commentPageQueryDTO));
    }

    /**
     * 修改评论状态
     * @param commentStatusUpdateDTO
     * @return
     */
    @PostMapping("/updateCommentStatus")
    public ResponseVO updateCommentStatus(@Valid CommentStatusUpdateDTO commentStatusUpdateDTO){
        commentService.updateCommentStatus(commentStatusUpdateDTO);
        return ResponseUtils.success();
    }
}
