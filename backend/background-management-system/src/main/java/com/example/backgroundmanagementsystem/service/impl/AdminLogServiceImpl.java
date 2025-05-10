package com.example.backgroundmanagementsystem.service.impl;

import com.example.backgroundmanagementsystem.service.AdminLogService;
import com.example.backgroundmanagementsystem.mapper.AdminLogMapper;
import com.example.backgroundmanagementsystem.pojo.dto.AdminLogAddDTO;
import com.example.backgroundmanagementsystem.pojo.dto.AdminLogPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.pojo.vo.AdminLogVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdminLogServiceImpl implements AdminLogService {
    private final AdminLogMapper logMapper;

    /**
     * 查询日志
     */
    @Override
    public PageResultVO loadLogList(AdminLogPageQueryDTO dto){
        PageHelper.startPage(dto.getPageNo(),dto.getPageSize());
        Page<AdminLogVO> page = logMapper.findBatch(dto);

        return new PageResultVO(
            dto.getPageNo(),
            dto.getPageSize(),
            page.getTotal(),
            page.getResult()
        );
    }

    /**
     * 删除日志
     */
    @Override
    public void deleteLog(Long logId,String adminName){
        log.info("删除日志：{}",logId);
        logMapper.deleteLog(logId);
        logMapper.addLog(adminName, "删除日志");
    }
    /**
     * 新增日志
     */
    @Override
    public Boolean addLog(AdminLogAddDTO dto){
        log.info("增加日志：{}",dto);
        String result = logMapper.checkPassword(dto);
        if(result!=null){
            logMapper.addLog(dto.getAdminName(),dto.getOperation());
            return true;
        }else{
            return false;
        }
    }
}
