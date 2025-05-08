package com.example.backgroundmanagementsystem.mapper;

import com.example.backgroundmanagementsystem.annotations.AutoFill;
import com.example.backgroundmanagementsystem.enums.OperationTypeEnum;
import com.example.backgroundmanagementsystem.pojo.dto.NoticePageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Notice;
import com.example.backgroundmanagementsystem.pojo.vo.NoticeVO;
import com.github.pagehelper.Page;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface NoticeMapper {

    @AutoFill(OperationTypeEnum.INSERT)
    void insert(Notice notice);

    @AutoFill(OperationTypeEnum.UPDATE)
    void update(Notice noticeFoUpdate);

    /**
     * 分页查询
     * @param notice
     * @return
     */
    Page<NoticeVO> findBatch(NoticePageQueryDTO notice);

    @Delete("delete from museum_notice where notice_id=#{noticeId}")
    void delete(Long noticeId);

    /**
     * 根据博物馆id删除
     * @param museumId
     */
    @Delete("delete from museum_notice where museum_id=#{museumId}")
    void deleteByMuseumId(Long museumId);
}
