package com.example.backgroundmanagementsystem.mapper;

import com.example.backgroundmanagementsystem.annotations.AutoFill;
import com.example.backgroundmanagementsystem.enums.OperationTypeEnum;
import com.example.backgroundmanagementsystem.pojo.dto.DataBaseBRPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.DataBaseBR;
import com.example.backgroundmanagementsystem.pojo.vo.DataBaseBRVO;
import com.github.pagehelper.Page;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DataBaseMapper {
    @AutoFill(OperationTypeEnum.INSERT)
    void insert(DataBaseBR databaseBR);

    Page<DataBaseBRVO> findBatch(DataBaseBRPageQueryDTO dataBaseBRPageQueryDTO);

    @Delete("delete from database_backup_recover where id=#{id}")
    void deleteById(Integer id);

    @AutoFill(OperationTypeEnum.UPDATE)
    void update(DataBaseBR dataBaseBR);

    @Select("select * from database_backup_recover where id=#{id}")
    DataBaseBR findById(Integer id);
}
