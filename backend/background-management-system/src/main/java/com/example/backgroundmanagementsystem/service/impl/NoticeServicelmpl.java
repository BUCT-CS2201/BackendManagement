package com.example.backgroundmanagementsystem.service.impl;

import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.exceptions.BaseException;
import com.example.backgroundmanagementsystem.mapper.AdminLogMapper;
import com.example.backgroundmanagementsystem.mapper.MuseumMapper;
import com.example.backgroundmanagementsystem.mapper.NoticeMapper;
import com.example.backgroundmanagementsystem.pojo.dto.NoticePageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Museum;
import com.example.backgroundmanagementsystem.pojo.entity.Notice;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.pojo.vo.NoticeVO;
import com.example.backgroundmanagementsystem.service.NoticeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServicelmpl implements NoticeService {
    private final NoticeMapper noticeMapper;
    private final MuseumMapper museumMapper;
    private final AdminLogMapper adminLogMapper;
    /**
     * 公告分页查询
     * @param noticePageQueryDTO
     * @return
     */
    @Override
    public PageResultVO loadNoticeList(NoticePageQueryDTO noticePageQueryDTO) {
        log.info("分页查询:{}",noticePageQueryDTO);
        PageHelper.startPage(noticePageQueryDTO.getPageNo(),noticePageQueryDTO.getPageSize());
        // 分页查询
        Page<NoticeVO> page = noticeMapper.findBatch(noticePageQueryDTO);
        return new PageResultVO(noticePageQueryDTO.getPageNo(),noticePageQueryDTO.getPageSize(),page.getTotal(),page.getResult());
    }

    
    @Override
    public void addOrUpdateNotice(Notice notice,String adminName) {
        log.info("新增或修改公告{}",notice);
        // 关联的博物馆不存在
        Museum museum = museumMapper.existById(notice.getMuseumId());
        if(null==museum){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"博物馆不存在");
        }
        // 新增
        if(null==notice.getNoticeId()){
            // 插入
            noticeMapper.insert(notice);
            adminLogMapper.addLog(adminName, "添加公告："+notice.getNoticeTitle());
        }
        else{
            // 修改
            noticeMapper.update(notice);
        }
    }

   /**
     * 删除公告
     * @param noticeId
     */
    @Override
    public void deleteNotice(Long noticeId,String adminName) {
        log.info("删除公告：{}",noticeId);
        noticeMapper.delete(noticeId);
        adminLogMapper.addLog(adminName, "删除公告："+noticeId);
    }
}
