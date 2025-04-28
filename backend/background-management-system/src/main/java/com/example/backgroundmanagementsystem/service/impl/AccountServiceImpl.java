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
import com.example.backgroundmanagementsystem.service.CheckCodeService;
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
    private final CheckCodeService checkCodeService;
    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    @Override
    public ResponseVO<UserTokenDTO> login(UserLoginDTO userLoginDTO) {
        log.info("登录：{}",userLoginDTO);
        // 1.用户是否存在
        User user = userMapper.findByPhoneNumber(userLoginDTO.getPhoneNumber());
        if(null==user){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"用户不存在");
        }
        // 2.密码是否正确
        if(!user.getPassword().equals(userLoginDTO.getPassword())){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"账号或密码错误");
        }
        // 3.用户是否被禁用
        if(!UserStatusEnum.ENABLE.getStatus().equals(user.getStatus())){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"用户已被禁用");
        }
        // 4.用户是否有权登录
        if(!UserRoleTypeEnum.ADMIN.getType().equals(user.getRoleType())){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"用户无权登录");
        }
        // todo 5.查redis，看用户是否已在线
        String token = StringUtils.getUserToken(user.getUserId());

        // 6.生成tokenDTO
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        BeanUtils.copyProperties(user,userTokenDTO);
        userTokenDTO.setToken(token);
        // todo 7.存入redis

        // 返回UserToken对象用于前端使用
        return ResponseUtils.success(userTokenDTO);
    }

    /**
     * 获取验证码
     * @param phoneNumber
     */
    @Override
    public void getCheckCode(String phoneNumber) {
        log.info("获取验证码：{}",phoneNumber);
        checkCodeService.generateCheckCode(phoneNumber);
    }

    @Override
    public void submitCheckCode(String phoneNumber, String checkCode) {
        log.info("校验短信验证码：phoneNumber={},checkCode={}",phoneNumber,checkCode);
        checkCodeService.validateCheckCode(phoneNumber,checkCode);
    }

    /**
     * 忘记密码并修改密码
     * @param phoneNumber
     * @param newPassword
     */
    @Override
    public void forgetAndChangePassword(String phoneNumber, String newPassword) {
        log.info("忘记密码并修改密码：phoneNumber={}",phoneNumber);
        User existUser = userMapper.findByPhoneNumber(phoneNumber);
        // 用户是否存在
        if (null==existUser){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"用户不存在");
        }
        // 修改密码
        User userFoUpdate = new User();
        userFoUpdate.setUserId(existUser.getUserId());
        userFoUpdate.setPassword(newPassword);
        userMapper.update(userFoUpdate);
    }
}
