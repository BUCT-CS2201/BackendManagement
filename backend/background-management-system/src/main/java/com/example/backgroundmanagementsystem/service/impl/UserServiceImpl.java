package com.example.backgroundmanagementsystem.service.impl;

import com.example.backgroundmanagementsystem.enums.OperationTypeEnum;
import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.enums.UserCommentStatusEnum;
import com.example.backgroundmanagementsystem.exceptions.BaseException;
import com.example.backgroundmanagementsystem.mapper.AdminLogMapper;
import com.example.backgroundmanagementsystem.mapper.UserMapper;
import com.example.backgroundmanagementsystem.pojo.dto.UserPageQueryDTO;
import com.example.backgroundmanagementsystem.pojo.entity.User;
import com.example.backgroundmanagementsystem.pojo.vo.PageResultVO;
import com.example.backgroundmanagementsystem.pojo.vo.UserVO;
import com.example.backgroundmanagementsystem.service.UserService;
import com.example.backgroundmanagementsystem.utils.LogUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.logging.LogManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Value("${service.default-password}")
    private String defaultPassword;
    private final UserMapper userMapper;
    private final AdminLogMapper adminLogMapper;
    /**
     * 用户分页查询
     * @param userPageQueryDTO
     * @return
     */
    @Override
    public PageResultVO loadUserList(UserPageQueryDTO userPageQueryDTO) {
        log.info("用户分页查询:{}",userPageQueryDTO);
        PageHelper.startPage(userPageQueryDTO.getPageNo(),userPageQueryDTO.getPageSize());
        // 分页查询
        Page<UserVO> page = userMapper.findBatch(userPageQueryDTO);
        return new PageResultVO(userPageQueryDTO.getPageNo(),userPageQueryDTO.getPageSize(),page.getTotal(),page.getResult());
    }

    /**
     * 新增或修改用户
     * @param user
     */
    @Override
    @Transactional // 事务
    public void addOrUpdateUser(User user,String adminName) {
        log.info("新增或修改用户{}",user);
        User existPhoneNumber = userMapper.findByPhoneNumber(user.getPhoneNumber());
        User existIdNumber = userMapper.findByIdNumber(user.getIdNumber());
        // 新增
        if(null==user.getUserId()){
            // 电话号码被使用
            if(null!=existPhoneNumber){
                throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"电话号码已被使用");
            }
            // 身份证号被使用
            if(null!=existIdNumber){
                throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"身份证号已被使用");
            }
            // 设置默认密码
            user.setPassword(defaultPassword);
            // 插入
            userMapper.insert(user);
            adminLogMapper.addLog(adminName, "新增用户:"+user.getPhoneNumber());
        }else{
            // 修改
            // 要修改的电话号码被使用
            if(null!=existPhoneNumber && !user.getUserId().equals(existPhoneNumber.getUserId())){
                throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"电话号码已被使用");
            }
            // 要修改的身份证号被使用
            if(null!=existIdNumber && !user.getUserId().equals(existIdNumber.getUserId())){
                throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"身份证号已被使用");
            }
            // 修改
            userMapper.update(user);
            adminLogMapper.addLog(adminName, "修改用户信息:"+user.getUserId());
            // logMapper.xxinsert()
        }
    }

    /**
     * 删除用户
     * @param userId
     */
    @Override
    @Transactional
    public void deleteUser(Long userId,String adminName) {
        log.info("删除用户：{}",userId);
        userMapper.delete(userId);
        adminLogMapper.addLog(adminName, "删除用户："+userId);
    }

    @Override
    public void updateUserCommentStatus(Long userId, Integer commentStatus) {
        log.info("修改用户评论状态：userId={}，commentStatus={}",userId,commentStatus);
        if(!ArrayUtils.contains(new int[]{UserCommentStatusEnum.ENABLE.getStatus(), UserCommentStatusEnum.DISABLE.getStatus()}, commentStatus)){
            throw new BaseException(ResponseCodeEnum.CODE_600);
        }
        User user = new User();
        user.setUserId(userId);
        user.setCommentStatus(commentStatus);
        userMapper.update(user);
    }
}
