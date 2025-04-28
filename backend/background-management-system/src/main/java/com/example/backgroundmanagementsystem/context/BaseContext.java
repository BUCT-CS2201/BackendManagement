package com.example.backgroundmanagementsystem.context;

import com.example.backgroundmanagementsystem.pojo.dto.UserTokenDTO;
import lombok.Data;

/**
 * 上下文信息存储
 */
@Data
public class BaseContext {
    /**
     * 在请求时拦截，存储用户Token信息
     */
    public static ThreadLocal<UserTokenDTO> threadLocal = new ThreadLocal<>();
    public static void setUserToken(UserTokenDTO tokenUserInfo){
        threadLocal.set(tokenUserInfo);
    }
    public static UserTokenDTO getUserToken(){
        return threadLocal.get();
    }
    public static void removeUserToken(){
        threadLocal.remove();
    }
}
