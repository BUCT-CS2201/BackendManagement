package com.example.backgroundmanagementsystem.service.impl;

import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.exceptions.BaseException;
import com.example.backgroundmanagementsystem.mapper.UserMapper;
import com.example.backgroundmanagementsystem.pojo.entity.User;
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
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdminLogServiceImpl implements AdminLogService {
    private final AdminLogMapper logMapper;
    private final UserMapper userMapper;

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
    @Transactional
    public void deleteLog(Long logId,String adminName){
        log.info("删除日志：{}",logId);
        logMapper.deleteLog(logId);
        logMapper.addLog(adminName, "删除日志:"+logId);
    }
    /**
     * 新增日志
     */
    @Override
    public void addLog(AdminLogAddDTO dto){
        log.info("增加日志：{}",dto);
        User user = userMapper.findByPhoneNumber(dto.getPhoneNumber());
        if(null==user || !user.getName().equals(dto.getAdminName()) || !user.getPassword().equals(dto.getPassword())){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"信息输入错误");
        }
        logMapper.addLog(dto.getAdminName(),dto.getOperation());
    }
}
