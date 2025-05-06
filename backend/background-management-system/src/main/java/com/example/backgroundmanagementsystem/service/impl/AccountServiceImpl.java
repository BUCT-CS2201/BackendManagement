package com.example.backgroundmanagementsystem.service.impl;
import com.example.backgroundmanagementsystem.context.BaseContext;
import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.enums.UserRoleTypeEnum;
import com.example.backgroundmanagementsystem.enums.UserAccountStatusEnum;
import com.example.backgroundmanagementsystem.exceptions.BaseException;
import com.example.backgroundmanagementsystem.mapper.UserMapper;
import com.example.backgroundmanagementsystem.pojo.dto.UserLoginDTO;
import com.example.backgroundmanagementsystem.pojo.dto.UserPasswordChangeDTO;
import com.example.backgroundmanagementsystem.pojo.dto.UserTokenDTO;
import com.example.backgroundmanagementsystem.pojo.entity.User;
import com.example.backgroundmanagementsystem.pojo.vo.ResponseVO;
import com.example.backgroundmanagementsystem.redis.RedisService;
import com.example.backgroundmanagementsystem.service.AccountService;
import com.example.backgroundmanagementsystem.service.CheckCodeService;
import com.example.backgroundmanagementsystem.utils.ResponseUtils;
import com.example.backgroundmanagementsystem.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final UserMapper userMapper;
    private final CheckCodeService checkCodeService;
    private final RedisService redisService;
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
        if(!UserAccountStatusEnum.ENABLE.getStatus().equals(user.getAccountStatus())){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"用户已被禁用");
        }
        // 4.用户是否有权登录
        if(!UserRoleTypeEnum.ADMIN.getType().equals(user.getRoleType())){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"用户无权登录");
        }
        // 5.查redis，看用户是否已在线
        String token = StringUtils.getUserToken(user.getUserId());
        if(null!=redisService.getUserTokenDTO(token)){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"用户已登录");
        }
        // 6.生成tokenDTO
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        BeanUtils.copyProperties(user,userTokenDTO);
        userTokenDTO.setToken(token);
        // 7.存入redis
        redisService.saveUserTokenDTO(token,userTokenDTO);
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

    /**
     * 退出登录
     * @param userId
     * @return
     */
    public void logout(Long userId){
        log.info("退出登录：userId={}",userId);
        User existUser = userMapper.findByUserId(userId);
        // 用户是否存在
        if (null==existUser){
            throw new BaseException(ResponseCodeEnum.CODE_600);
        }
        // 当前操作用户是否拥有权限
        UserTokenDTO userTokenDTO = BaseContext.getUserToken();
        // 执行当前操作的用户id == 与要退出登录的用户id
        if (!userTokenDTO.getUserId().equals(userId)){
            throw new BaseException(ResponseCodeEnum.CODE_600);
        }
        // 清除redis缓存
        redisService.removeUserTokenDTO(userTokenDTO.getToken());
    }
    /**
     * 修改密码
     * @param userPasswordChangeDTO
     */
    public void changePassword(UserPasswordChangeDTO userPasswordChangeDTO){
        Long userId = userPasswordChangeDTO.getUserId();
        String oldPassword = userPasswordChangeDTO.getOldPassword();
        String newPassword = userPasswordChangeDTO.getNewPassword();
        log.info("修改密码：userId={}",userId);
        // 当前操作用户是否拥有权限
        UserTokenDTO tokenUserInfo = BaseContext.getUserToken();
        if (!tokenUserInfo.getUserId().equals(userId)){
            throw new BaseException(ResponseCodeEnum.CODE_600);
        }
        User existUser = userMapper.findByUserId(userId);
        // 用户是否存在或旧密码是否匹配
        if (null==existUser || !existUser.getPassword().equals(oldPassword)){
            throw new BaseException(ResponseCodeEnum.CODE_400.getCode(),"旧密码输入错误");
        }
        // 修改密码
        User userFoUpdate = new User();
        userFoUpdate.setUserId(userId);
        userFoUpdate.setPassword(newPassword);
        userMapper.update(userFoUpdate);
    }
}
