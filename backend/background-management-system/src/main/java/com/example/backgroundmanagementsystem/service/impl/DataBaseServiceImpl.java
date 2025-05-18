package com.example.backgroundmanagementsystem.service.impl;

import com.example.backgroundmanagementsystem.context.BaseContext;
import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.exceptions.BaseException;
import com.example.backgroundmanagementsystem.mapper.AdminLogMapper;
import com.example.backgroundmanagementsystem.mapper.DataBaseMapper;
import com.example.backgroundmanagementsystem.pojo.dto.DataBaseBRPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.BaseEntity;
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

import java.io.File;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataBaseServiceImpl implements DataBaseService {
    private final DataBaseUtils dataBaseUtils;
    private final DataBaseMapper dataBaseMapper;
    private final AdminLogMapper adminLogMapper;
    @Override
    @Transactional
    public void addOrUpdateBackup(DataBaseBR dataBaseBR) {
        log.info("添加或修改数据库备份：{}",dataBaseBR);
        // 添加
        if(null==dataBaseBR.getId()){
            String path = dataBaseBR.getPath();
            Boolean success = dataBaseUtils.dbBackUp(path);
            if(!success){
                throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"数据库备份失败");
            }
            DataBaseBR dataBaseBRForInsert = new DataBaseBR();
            String adminName = dataBaseBR.getAdminName();
            adminName = StringUtils.isEmpty(adminName)? BaseContext.getUserToken().getName() : adminName;
            dataBaseBRForInsert.setAdminName(adminName);
            dataBaseBRForInsert.setComment(dataBaseBR.getComment());
            dataBaseBRForInsert.setPath(path);
            dataBaseMapper.insert(dataBaseBRForInsert);
            adminLogMapper.addLog(BaseContext.getUserToken().getName(),"进行数据库备份:"+dataBaseBRForInsert.getId());
        }else{ // 修改
            dataBaseMapper.update(dataBaseBR);
            adminLogMapper.addLog(BaseContext.getUserToken().getName(),"进行数据库修改:"+dataBaseBR.getId());
        }
    }

    @Override
    public PageResultVO loadDataBaseList(DataBaseBRPageQueryDTO dataBaseBRPageQueryDTO) {
        log.info("加载备份列表：{}",dataBaseBRPageQueryDTO);
        PageHelper.startPage(dataBaseBRPageQueryDTO.getPageNo(),dataBaseBRPageQueryDTO.getPageSize());
        Page<DataBaseBRVO> page = dataBaseMapper.findBatch(dataBaseBRPageQueryDTO);
        return new PageResultVO(dataBaseBRPageQueryDTO.getPageNo(),dataBaseBRPageQueryDTO.getPageSize(),page.getTotal(),page.getResult());
    }

    @Transactional
    @Override
    public void deleteDataBaseBackup(Integer id) {
        log.info("删除备份记录：{}",id);
        // 删除备份文件
        DataBaseBR dataBaseBR = dataBaseMapper.findById(id);
        File file = new File(dataBaseBR.getPath());
        if(file.exists()){
            file.delete();
        }
        dataBaseMapper.deleteById(id);
        adminLogMapper.addLog(BaseContext.getUserToken().getName(),"删除数据库备份:"+id);
    }

    @Override
    public String getDefaultPath() {
        log.info("获取默认备份路径");
        return dataBaseUtils.getDefaultPath();
    }

    @Override
    @Transactional
    public void recover(Integer id) {
        log.info("数据库恢复：{}",id);
        DataBaseBR dataBaseBR = dataBaseMapper.findById(id);
        if(null==dataBaseBR){
            throw new BaseException(ResponseCodeEnum.CODE_600);
        }
        File file = new File(dataBaseBR.getPath());
        if(!file.exists()){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"该备份文件已被删除");
        }
        boolean success = dataBaseUtils.dbRestore(dataBaseBR.getPath());
        if(success){
            adminLogMapper.addLog(BaseContext.getUserToken().getName(),"恢复数据库备份:"+id);
            log.info("恢复成功");
        }else{
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"恢复失败");
        }
    }
}
