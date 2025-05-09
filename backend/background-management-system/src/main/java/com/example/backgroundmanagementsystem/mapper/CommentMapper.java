package com.example.backgroundmanagementsystem.mapper;

import com.example.backgroundmanagementsystem.annotations.AutoFill;
import com.example.backgroundmanagementsystem.enums.OperationTypeEnum;
import com.example.backgroundmanagementsystem.pojo.dto.CommentPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Comment;
import com.example.backgroundmanagementsystem.pojo.vo.CommentVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

@Mapper
public interface CommentMapper {

    Page<CommentVO> findBatch(CommentPageQueryDTO commentPageQueryDTO);

    @Update("update relic_comment set status=#{status} where comment_id=#{commentId}")
    @AutoFill(OperationTypeEnum.UPDATE)
    void updateStatusById(Comment comment);
}
