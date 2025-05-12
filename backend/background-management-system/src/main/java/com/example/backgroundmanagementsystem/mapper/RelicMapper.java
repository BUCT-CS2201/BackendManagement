package com.example.backgroundmanagementsystem.mapper;

import com.example.backgroundmanagementsystem.annotations.AutoFill;
import com.example.backgroundmanagementsystem.enums.OperationTypeEnum;
import com.example.backgroundmanagementsystem.pojo.dto.RelicPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.Relic;
import com.example.backgroundmanagementsystem.pojo.vo.RelicVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface RelicMapper {

    @AutoFill(OperationTypeEnum.INSERT)
    void insert(Relic cultural_relic);

    @Select("select * from cultural_relic where name=#{name}")
    Relic findByName(String name);

    @AutoFill(OperationTypeEnum.UPDATE)
    void update(Relic relicFoUpdate);

    /**
     * 分页查询
     * @param cultural_relic
     * @return
     */
    Page<RelicVO> findBatch(RelicPageQueryDTO cultural_relic);

    @Delete("delete from cultural_relic where relic_id=#{relicId}")
    void delete(Long relicId);
}
