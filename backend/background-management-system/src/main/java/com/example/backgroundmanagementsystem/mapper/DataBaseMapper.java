package com.example.backgroundmanagementsystem.mapper;

import com.example.backgroundmanagementsystem.annotations.AutoFill;
import com.example.backgroundmanagementsystem.enums.OperationTypeEnum;
import com.example.backgroundmanagementsystem.pojo.dto.DataBaseBRPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.DataBaseBR;
import com.example.backgroundmanagementsystem.pojo.vo.DataBaseBRVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataBaseMapper {
    @AutoFill(OperationTypeEnum.INSERT)
    void insert(DataBaseBR databaseBR);

    Page<DataBaseBRVO> findBatch(DataBaseBRPageQueryDTO dataBaseBRPageQueryDTO);
}
