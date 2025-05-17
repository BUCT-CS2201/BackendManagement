package com.example.backgroundmanagementsystem.service;

import com.example.backgroundmanagementsystem.pojo.dto.DataBaseBRPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;

public interface DataBaseService {
    /**
     * 备份
     * @param comment
     */
    void backup(String comment);

    /**
     * 加载备份列表
     * @param dataBaseBRPageQueryDTO
     * @return
     */
    PageResultVO loadDataBaseList(DataBaseBRPageQueryDTO dataBaseBRPageQueryDTO);
}
