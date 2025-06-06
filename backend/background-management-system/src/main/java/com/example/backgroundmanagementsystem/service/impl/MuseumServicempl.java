package com.example.backgroundmanagementsystem.service.impl;

import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.exceptions.BaseException;
import com.example.backgroundmanagementsystem.mapper.AdminLogMapper;
import com.example.backgroundmanagementsystem.mapper.MuseumMapper;
import com.example.backgroundmanagementsystem.mapper.NoticeMapper;
import com.example.backgroundmanagementsystem.pojo.dto.MuseumPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Museum;
import com.example.backgroundmanagementsystem.pojo.vo.MuseumVO;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.service.MuseumService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MuseumServicempl implements MuseumService {
    private final MuseumMapper museumMapper;
    private final NoticeMapper noticeMapper;
    private final AdminLogMapper adminLogMapper;
    /**
     * 博物馆分页查询
     * @param museumPageQueryDTO
     * @return
     */
    @Override
    public PageResultVO loadMuseumList(MuseumPageQueryDTO museumPageQueryDTO) {
        log.info("博物馆分页查询:{}",museumPageQueryDTO);
        PageHelper.startPage(museumPageQueryDTO.getPageNo(),museumPageQueryDTO.getPageSize());
        // 分页查询
        Page<MuseumVO> page = museumMapper.findBatch(museumPageQueryDTO);
        return new PageResultVO(museumPageQueryDTO.getPageNo(), museumPageQueryDTO.getPageSize(), page.getTotal(), page.getResult());
    }

    /**
     * 新增或修改博物馆
     * @param museum
     */
    @Override
    @Transactional
    public void addOrUpdateMuseum(Museum museum,String adminName) {
        log.info("新增或修改博物馆{}",museum);
        Museum existMuseumName = museumMapper.findByMuseumName(museum.getMuseumName());
        // 新增
        if(null==museum.getMuseumId()){
            // 博物馆名称被使用
            if(null!=existMuseumName){
                throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"该名称已被使用");
            }
            // 插入
            museumMapper.insert(museum);
            adminLogMapper.addLog(adminName, "添加博物馆："+museum.getMuseumName());
        }else{
            // 修改
            // 要修改的名称被使用
            if(null!=existMuseumName && !museum.getMuseumId().equals(existMuseumName.getMuseumId())){
                throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"该名称已被使用");
            }
            // 修改
            museumMapper.update(museum);
            adminLogMapper.addLog(adminName, "修改博物馆："+museum.getMuseumId());
        }
    }

    /**
     * 删除博物馆
     * @param museumId
     */
    @Override
    @Transactional
    public void deleteMuseum(Long museumId,String adminName) {
        log.info("删除博物馆：{}",museumId);
        // 删除关联的公告
        noticeMapper.deleteByMuseumId(museumId);
        museumMapper.delete(museumId);
        adminLogMapper.addLog(adminName, "删除博物馆:"+museumId);
    }
}
