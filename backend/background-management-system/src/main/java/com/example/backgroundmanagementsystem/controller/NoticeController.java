package com.example.backgroundmanagementsystem.controller;

import com.example.backgroundmanagementsystem.annotations.PageQueryAutoFill;
import com.example.backgroundmanagementsystem.mapper.NoticeMapper;
import com.example.backgroundmanagementsystem.pojo.dto.NoticePageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Notice;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.pojo.vo.ResponseVO;
import com.example.backgroundmanagementsystem.pojo.vo.NoticeVO;
import com.example.backgroundmanagementsystem.service.NoticeService;
import com.example.backgroundmanagementsystem.utils.ResponseUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    /**
     * 加载公告列表
     * @param noticePageQueryDTO
     * @return
     */
    @PostMapping("/loadNoticeList")
    @PageQueryAutoFill
    public ResponseVO<PageResultVO> loadNoticeList(NoticePageQueryDTO noticePageQueryDTO){
        PageResultVO ans = noticeService.loadNoticeList(noticePageQueryDTO);
        return ResponseUtils.success(ans);
    }

    /**
     * 添加或修改公告
     * @param notice
     * @return
     */
    @PostMapping("/addOrUpdateNotice")
    public ResponseVO addOrUpdateNotice(@Valid Notice notice){
        noticeService.addOrUpdateNotice(notice);
        return ResponseUtils.success();
    }

    /**
     * 删除公告
     * @param noticeId
     * @return
     */
    @PostMapping("/deleteNotice")
    public ResponseVO deleteNotice(@NotNull Long noticeId){
        noticeService.deleteNotice(noticeId);
        return ResponseUtils.success();
    }
}
