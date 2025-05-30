package com.example.backgroundmanagementsystem.service;

import com.example.backgroundmanagementsystem.pojo.dto.UserPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.User;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;

public interface UserService {

    /**
     * 用户分页查询
     * @param userPageQueryDTO
     * @return
     */
    PageResultVO loadUserList(UserPageQueryDTO userPageQueryDTO);

    /**
     * 新增或修改用户
     * @param user
     */
    void addOrUpdateUser(User user,String adminName);

    /**
     * 删除用户
     * @param userId
     */
    void deleteUser(Long userId,String adminName);

    /**
     * 修改用户评论状态
     * @param userId
     * @param commentStatus
     */
    void updateUserCommentStatus(Long userId, Integer commentStatus);
}
