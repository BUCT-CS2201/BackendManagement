package com.example.backgroundmanagementsystem.mapper;

import com.example.backgroundmanagementsystem.annotations.AutoFill;
import com.example.backgroundmanagementsystem.enums.OperationTypeEnum;
import com.example.backgroundmanagementsystem.pojo.dto.MuseumPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Museum;
import com.example.backgroundmanagementsystem.pojo.vo.MuseumVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MuseumMapper {
    @AutoFill(OperationTypeEnum.INSERT)
    void insert(Museum museum);

    @Select("select * from museum where museum_name=#{museumName}")
    Museum findByMuseumName(String museumName);

    @AutoFill(OperationTypeEnum.UPDATE)
    void update(Museum museumFoUpdate);

    /**
     * 分页查询
     * @param museum
     * @return
     */
    Page<MuseumVO> findBatch(MuseumPageQueryDTO museum);

    @Delete("delete from museum where museum_id=#{museumId}")
    void delete(Long museumId);
}
