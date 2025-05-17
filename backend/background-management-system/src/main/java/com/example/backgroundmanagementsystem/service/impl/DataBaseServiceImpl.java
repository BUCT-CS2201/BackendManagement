package com.example.backgroundmanagementsystem.service.impl;

import com.example.backgroundmanagementsystem.context.BaseContext;
import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.exceptions.BaseException;
import com.example.backgroundmanagementsystem.mapper.AdminLogMapper;
import com.example.backgroundmanagementsystem.mapper.DataBaseMapper;
import com.example.backgroundmanagementsystem.pojo.dto.DataBaseBRPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.DataBaseBR;
import com.example.backgroundmanagementsystem.pojo.vo.DataBaseBRVO;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.service.DataBaseService;
import com.example.backgroundmanagementsystem.utils.DataBaseUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataBaseServiceImpl implements DataBaseService {
    private final DataBaseUtils dataBaseUtils;
    private final DataBaseMapper dataBaseMapper;
    private final AdminLogMapper adminLogMapper;
    @Override
    @Transactional
    public void backup(String comment) {
        log.info("数据库备份");
        String path = dataBaseUtils.dbBackUp();
        if(StringUtils.isEmpty(path)){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"数据库备份失败");
        }else{
            DataBaseBR databaseBR = new DataBaseBR();
            databaseBR.setAdminName(BaseContext.getUserToken().getName());
            databaseBR.setComment(comment);
            databaseBR.setPath(path);
            dataBaseMapper.insert(databaseBR);
            adminLogMapper.addLog(BaseContext.getUserToken().getName(),"进行数据库备份:{}"+databaseBR.getId());
        }
    }

    @Override
    public PageResultVO loadDataBaseList(DataBaseBRPageQueryDTO dataBaseBRPageQueryDTO) {
        log.info("加载备份列表：{}",dataBaseBRPageQueryDTO);
        PageHelper.startPage(dataBaseBRPageQueryDTO.getPageNo(),dataBaseBRPageQueryDTO.getPageSize());
        Page<DataBaseBRVO> page = dataBaseMapper.findBatch(dataBaseBRPageQueryDTO);
        return new PageResultVO(dataBaseBRPageQueryDTO.getPageNo(),dataBaseBRPageQueryDTO.getPageSize(),page.getTotal(),page.getResult());
    }
}
