package com.example.backgroundmanagementsystem.mapper;

import com.example.backgroundmanagementsystem.annotations.AutoFill;
import com.example.backgroundmanagementsystem.enums.OperationTypeEnum;
import com.example.backgroundmanagementsystem.pojo.entity.User;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @AutoFill(OperationTypeEnum.INSERT)
    void insert(User user);

    @Select("select * from user where phone_number=#{phoneNumber}")
    User findByPhoneNumber(String phoneNumber);

    @AutoFill(OperationTypeEnum.UPDATE)
    void update(User userFoUpdate);

    /**
     * 分页查询
     * @param user
     * @return
     */
    Page<User> findBatch(User user);
}
