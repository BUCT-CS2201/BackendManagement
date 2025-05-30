package com.example.backgroundmanagementsystem.interceptor;

import com.example.backgroundmanagementsystem.context.BaseContext;
import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.exceptions.BaseException;
import com.example.backgroundmanagementsystem.pojo.dto.UserTokenDTO;
import com.example.backgroundmanagementsystem.redis.RedisService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 用户token拦截器
 */
@Component
@Slf4j
public class UserTokenInterceptor implements HandlerInterceptor {
    @Resource
    private RedisService redisService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }
        // 取出请求头中的token
        String token = request.getHeader("token");
        // 取出redis中的信息
        UserTokenDTO userTokenDTO = redisService.getUserTokenDTO(token);
        if (null==userTokenDTO){
            throw new BaseException(ResponseCodeEnum.CODE_401);
        } else{
            // 存入BaseContext
            BaseContext.setUserToken(userTokenDTO);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清除ThreadLocal信息，防止OOM
        BaseContext.removeUserToken();
    }
}
