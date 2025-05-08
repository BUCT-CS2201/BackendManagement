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
    void addOrUpdateNotice(Notice notice);

    /**
     * 删除公告
     * @param notice_id
     */
    void deleteNotice(Long notice_id);
}
