package com.example.backgroundmanagementsystem.service;

import com.example.backgroundmanagementsystem.pojo.dto.RelicPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Relic;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;

public interface RelicService {

    /**
     * 用户分页查询
     * @param relicPageQueryDTO
     * @return
     */
    PageResultVO loadRelicList(RelicPageQueryDTO relicPageQueryDTO);

    /**
     * 新增或修改用户
     * @param cultural_relic
     */
    void addOrUpdateRelic(Relic cultural_relic,String adminName);

    /**
     * 删除用户
     * @param relicId
     */
    void deleteRelic(Long relicId,String adminName);
}
