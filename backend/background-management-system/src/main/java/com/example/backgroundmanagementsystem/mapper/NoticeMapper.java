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

    @Select("select * from notice where title=#{title}")
    Notice findByTitle(String title);

    @Select("select * from name where name=#{name}")
    Notice findByName(String name);

    @Select("select * from notice where notice_id=#{notice_id}")
    Notice findByNoticeId(Long notice_id);

    @AutoFill(OperationTypeEnum.UPDATE)
    void update(Notice noticeFoUpdate);

    /**
     * 分页查询
     * @param notice
     * @return
     */
    Page<NoticeVO> findBatch(NoticePageQueryDTO notice);

    @Delete("delete from notice where notice_id=#{notice_id}")
    void delete(Long notice_id);


    List<NoticeVO> findall();
}
