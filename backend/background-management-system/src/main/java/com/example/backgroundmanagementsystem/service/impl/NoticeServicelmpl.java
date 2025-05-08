package com.example.backgroundmanagementsystem.service.impl;

import com.example.backgroundmanagementsystem.enums.OperationTypeEnum;
import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.exceptions.BaseException;
import com.example.backgroundmanagementsystem.mapper.NoticeMapper;
import com.example.backgroundmanagementsystem.pojo.dto.NoticePageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Notice;
import com.example.backgroundmanagementsystem.pojo.entity.User;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.pojo.vo.NoticeVO;
import com.example.backgroundmanagementsystem.service.NoticeService;
import com.example.backgroundmanagementsystem.utils.LogUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.logging.LogManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServicelmpl implements NoticeService {
    @Value("${service.default-password}")
    private String defaultPassword;
    private final NoticeMapper noticeMapper;
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
    public void addOrUpdateNotice(Notice notice) {
        log.info("新增或修改公告{}",notice);
        Notice existTitle = noticeMapper.findByTitle(notice.getTitle());
        // 新增
        if(null==notice.getNotice_id()){
            // 公告标题被使用
            if(null!=existTitle){
                throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"标题已被使用");
            }
            // 设置时间
            LocalDateTime a=LocalDateTime.now();
            LocalDateTime aa=a.truncatedTo(ChronoUnit.SECONDS);
            notice.setTime(aa);
            // 插入
            noticeMapper.insert(notice);
        }
        else{
            // 修改
            // 修改
            noticeMapper.update(notice);
        }
    }

   /**
     * 删除公告
     * @param userId
     */
    @Override
    public void deleteNotice(Long notice_id) {
        log.info("删除公告：{}",notice_id);
        noticeMapper.delete(notice_id);
    }
}
