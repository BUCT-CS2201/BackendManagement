package com.example.backgroundmanagementsystem.service;

import com.example.backgroundmanagementsystem.pojo.dto.MuseumPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Museum;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;

public interface MuseumService {

    /**
     * 博物馆分页查询
     * @param museumPageQueryDTO
     * @return
     */
    PageResultVO loadMuseumList(MuseumPageQueryDTO museumPageQueryDTO);
    /**
     * 新增或修改博物馆
     * @param museum
     */
    void addOrUpdateMuseum(Museum museum);

    /**
     * 删除博物馆
     * @param museumId
     */
    void deleteMuseum(Long museumId);
}