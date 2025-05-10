package com.example.backgroundmanagementsystem.mapper;

import com.example.backgroundmanagementsystem.annotations.AutoFill;
import com.example.backgroundmanagementsystem.enums.OperationTypeEnum;
import com.example.backgroundmanagementsystem.pojo.dto.CommentPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Comment;
import com.example.backgroundmanagementsystem.pojo.vo.CommentVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface CommentMapper {

    Page<CommentVO> findBatch(CommentPageQueryDTO commentPageQueryDTO);

    @Update("update relic_comment set status=#{status},update_time=#{updateTime} where comment_id=#{commentId}")
    @AutoFill(OperationTypeEnum.UPDATE)
    void updateStatusByCommentId(Comment comment);

    @Update("update relic_comment set status=#{status},update_time=#{updateTime} where parent_id=#{parentId}")
    @AutoFill(OperationTypeEnum.UPDATE)
    void updateStatusByParentId(Comment comment);

    @Select("select * from relic_comment where comment_id=#{commentId}")
    Comment findByCommentId(Long commentId);
}
