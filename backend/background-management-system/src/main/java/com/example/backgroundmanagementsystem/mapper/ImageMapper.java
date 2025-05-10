package com.example.backgroundmanagementsystem.mapper;

import com.example.backgroundmanagementsystem.annotations.AutoFill;
import com.example.backgroundmanagementsystem.enums.OperationTypeEnum;
import com.example.backgroundmanagementsystem.pojo.dto.UserImagePageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.UserImage;
import com.example.backgroundmanagementsystem.pojo.vo.UserImageVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ImageMapper {
    /**
     * 分页查询
     * @param userImagePageQueryDTO
     * @return
     */
    Page<UserImageVO> findBatch(UserImagePageQueryDTO userImagePageQueryDTO);

    /**
     * 修改图片状态
     * @param userImage
     */
    @Update("update user_image set status=#{status},update_time=#{updateTime} where image_id=#{imageId}")
    @AutoFill(OperationTypeEnum.UPDATE)
    void updateStatusByImageId(UserImage userImage);
}
