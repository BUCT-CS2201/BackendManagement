package com.example.backgroundmanagementsystem.mapper;

import com.example.backgroundmanagementsystem.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where phone_number=#{phoneNumber}")
    User findByPhoneNumber(String phoneNumber);
}
