package com.example.backgroundmanagementsystem.service;

import com.example.backgroundmanagementsystem.pojo.dto.AdminLogPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.dto.AdminLogAddDTO;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;

public interface AdminLogService {
    /**
     * 获取日志数据
     * @param dto
     * @return
     */
    PageResultVO loadLogList(AdminLogPageQueryDTO dto);

    /**
     * 删除日志
     * @param logId
     */
    void deleteLog(Long logId,String adminName);
    
    /**
     * 增加日志
     * @param dto
     */
    
    void addLog(AdminLogAddDTO dto);
}
