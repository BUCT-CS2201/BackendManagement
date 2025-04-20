package com.example.backgroundmanagementsystem.service.impl;
import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.enums.UserRoleTypeEnum;
import com.example.backgroundmanagementsystem.enums.UserStatusEnum;
import com.example.backgroundmanagementsystem.exceptions.BaseException;
import com.example.backgroundmanagementsystem.mapper.UserMapper;
import com.example.backgroundmanagementsystem.pojo.dto.UserLoginDTO;
import com.example.backgroundmanagementsystem.pojo.dto.UserTokenDTO;
import com.example.backgroundmanagementsystem.pojo.entity.User;
import com.example.backgroundmanagementsystem.pojo.vo.ResponseVO;
import com.example.backgroundmanagementsystem.service.AccountService;
import com.example.backgroundmanagementsystem.utils.ResponseUtils;
import com.example.backgroundmanagementsystem.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final UserMapper userMapper;
    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    @Override
    public ResponseVO<UserTokenDTO> login(UserLoginDTO userLoginDTO) {
        log.info("登录：{}",userLoginDTO);
        User user = userMapper.findByPhoneNumber(userLoginDTO.getPhoneNumber());
        // 账号校验
        if(null==user){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"用户不存在，请先注册");
        }
        // 无权登录
        if(!UserRoleTypeEnum.ADMIN.getType().equals(user.getRoleType())){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"用户无权登录");
        }
        // 用户被禁用
        if(UserStatusEnum.DISABLE.getStatus().equals(user.getStatus())){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"用户已被禁用");
        }
        // 密码校验
        if(!user.getPassword().equals(userLoginDTO.getPassword())){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"密码错误");
        }
        // todo redis验证用户是否在线，防重复登陆

        // 初次登录，生成用户token
        String token = StringUtils.generateUserToken(user.getUserId());
        // 封装tokenDTO
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        userTokenDTO.setToken(token);
        BeanUtils.copyProperties(user,userTokenDTO);
        // todo UserToken存入redis

        // 返回UserToken对象用于前端使用
        return ResponseUtils.success(userTokenDTO);
    }
}
