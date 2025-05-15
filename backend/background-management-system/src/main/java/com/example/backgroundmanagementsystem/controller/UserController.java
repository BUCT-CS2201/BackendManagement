package com.example.backgroundmanagementsystem.controller;

import com.example.backgroundmanagementsystem.annotations.PageQueryAutoFill;
import com.example.backgroundmanagementsystem.pojo.dto.UserPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.User;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.pojo.vo.ResponseVO;
import com.example.backgroundmanagementsystem.service.UserService;
import com.example.backgroundmanagementsystem.utils.ResponseUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 加载用户列表
     * @param userPageQueryDTO
     * @return
     */
    @PostMapping("/loadUserList")
    @PageQueryAutoFill
    public ResponseVO<PageResultVO> loadUserList(UserPageQueryDTO userPageQueryDTO){
        return ResponseUtils.success(userService.loadUserList(userPageQueryDTO));
    }

    /**
     * 添加或修改用户
     * @param user
     * @return
     */
    @PostMapping("/addOrUpdateUser")
    public ResponseVO addOrUpdateUser(@Valid User user,String adminName){
        userService.addOrUpdateUser(user,adminName);
        return ResponseUtils.success();
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @PostMapping("/deleteUser")
    public ResponseVO deleteUser(@NotNull Long userId,String adminName){
        userService.deleteUser(userId,adminName);
        return ResponseUtils.success();
    }

    /**
     * 修改用户评论状态
     * @param userId
     * @param commentStatus
     * @return
     */
    @PostMapping("/updateUserCommentStatus")
    public ResponseVO updateUserCommentStatus(@NotNull Long userId,@NotNull Integer commentStatus){
        userService.updateUserCommentStatus(userId,commentStatus);
        return ResponseUtils.success();
    }
}
