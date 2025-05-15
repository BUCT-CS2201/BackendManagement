package com.example.backgroundmanagementsystem.service;

import com.example.backgroundmanagementsystem.pojo.dto.NoticePageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Notice;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;

public interface NoticeService {

    /**
     * 分页查询
     * @param noticePageQueryDTO
     * @return
     */
    PageResultVO loadNoticeList(NoticePageQueryDTO noticePageQueryDTO);

    /**
     * 新增公告
     * @param notice
     */
    void addOrUpdateNotice(Notice notice,String adminName);

    /**
     * 删除公告
     * @param noticeId
     */
    void deleteNotice(Long noticeId,String adminName);
}
