package com.example.backgroundmanagementsystem.service;

import com.example.backgroundmanagementsystem.pojo.dto.DataBaseBRPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.DataBaseBR;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;

public interface DataBaseService {
    /**
     * 添加或修改备份
     * @param dataBaseBR
     */
    void addOrUpdateBackup(DataBaseBR dataBaseBR);

    /**
     * 加载备份列表
     * @param dataBaseBRPageQueryDTO
     * @return
     */
    PageResultVO loadDataBaseList(DataBaseBRPageQueryDTO dataBaseBRPageQueryDTO);

    /**
     * 删除备份记录
     * @param id
     */
    void deleteDataBaseBackup(Integer id);

    /**
     * 获取默认备份路径
     * @return
     */
    String getDefaultPath();

    /**
     * 数据库恢复
     * @param id
     */
    void recover(Integer id);
}
