package com.example.backgroundmanagementsystem.service.impl;

import com.example.backgroundmanagementsystem.enums.OperationTypeEnum;
import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.exceptions.BaseException;
import com.example.backgroundmanagementsystem.mapper.RelicMapper;
import com.example.backgroundmanagementsystem.pojo.dto.RelicPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Relic;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.pojo.vo.RelicVO;
import com.example.backgroundmanagementsystem.service.RelicService;
import com.example.backgroundmanagementsystem.utils.LogUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.logging.LogManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelicServiceImpl implements RelicService {
    private final RelicMapper relicMapper;
    /**
     * 文物分页查询
     * @param relicPageQueryDTO
     * @return
     */
    @Override
    public PageResultVO loadRelicList(RelicPageQueryDTO relicPageQueryDTO) {
        log.info("文物分页查询:{}",relicPageQueryDTO);
        PageHelper.startPage(relicPageQueryDTO.getPageNo(),relicPageQueryDTO.getPageSize());
        // 分页查询
        Page<RelicVO> page = relicMapper.findBatch(relicPageQueryDTO);
        return new PageResultVO(relicPageQueryDTO.getPageNo(),relicPageQueryDTO.getPageSize(),page.getTotal(),page.getResult());
    }

    /**
     * 新增或修改文物
     * @param cultural_relic
     */
    @Override
    public void addOrUpdateRelic(Relic cultural_relic) {
        log.info("新增或修改文物{}", cultural_relic);
        Relic existName = relicMapper.findByName(cultural_relic.getName());
        // 新增
        if (cultural_relic.getRelicId() == null) {
            // 文物名称被使用
            if (existName != null) {
                throw new BaseException(ResponseCodeEnum.CODE_400.getCode(), "文物名称已被使用");
            }
            // 插入
            relicMapper.insert(cultural_relic);
        } else {
            // 修改
            // 要修改的文物名称被其他文物使用
            if (existName != null && !cultural_relic.getRelicId().equals(existName.getRelicId())) {
                throw new BaseException(ResponseCodeEnum.CODE_400.getCode(), "文物名称已被使用");
            }
            // 修改
            relicMapper.update(cultural_relic);
        }
    }

    /**
     * 删除文物
     * @param relicId
     */
    @Override
    public void deleteRelic(Long relicId) {
        log.info("删除文物：{}",relicId);
        relicMapper.delete(relicId);
    }
}
