package com.example.backgroundmanagementsystem.mapper;

import com.example.backgroundmanagementsystem.pojo.dto.AdminLogPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.dto.AdminLogAddDTO;
import com.example.backgroundmanagementsystem.pojo.entity.AdminLog;
import com.example.backgroundmanagementsystem.pojo.vo.AdminLogVO;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminLogMapper {
    /**
     * 分页查询日志列表
     * @param dto 查询条件
     * @return 分页结果
     */
    Page<AdminLogVO> findBatch(AdminLogPageQueryDTO dto);

    /**
     * 查询日志总数
     */
    @Select("select count(*) from admin_log")
    Long countAll();

    /**
     * 删除日志
     */
    @Delete("delete from admin_log where log_id=#{logId}")
    void deleteLog(Long logId);

    /**
     * 增加日志
     */
    @Insert("insert into admin_log (admin_name,operation,operation_time) " +
            "values (#{admin_name},#{operation}, now())")
    void addLog(String admin_name, String operation);
}
