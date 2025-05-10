package com.example.backgroundmanagementsystem.service.impl;

import com.example.backgroundmanagementsystem.mapper.RelicMapper;
import com.example.backgroundmanagementsystem.pojo.dto.RelicPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Relic;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.pojo.vo.RelicVO;
import com.example.backgroundmanagementsystem.service.RelicService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    }

    /**
     * 删除文物
     * @param relicId
     */
    @Override
    public void deleteRelic(Long relicId) {
        log.info("删除用户：{}",relicId);
        relicMapper.delete(relicId);
    }
}
