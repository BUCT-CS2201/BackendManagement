package com.example.backgroundmanagementsystem.utils;

import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import com.example.backgroundmanagementsystem.pojo.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;

/**
 * 响应结果工具类
 */
public class ResponseUtils {
    /**
     * 操作成功，返回自定义提示信息 + 数据
     * @param msg
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ResponseVO<T> success(String msg,T data){
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setCode(ResponseCodeEnum.CODE_200.getCode());
        responseVO.setInfo(msg);
        responseVO.setData(data);
        return responseVO;
    }

    /**
     * 操作成功，返回默认提示信息 + 数据
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ResponseVO<T> success(T data){
        return success(ResponseCodeEnum.CODE_200.getMsg(), data);
    }


    /**
     * 操作成功，返回默认提示信息
     * @return
     */
    public static ResponseVO success(){
        return success(null);
    }
    /**
     * 操作有误
     *
     * @param code 错误码
     * @param msg  提示信息
     */
    public static ResponseVO error(Integer code, String msg) {
        ResponseVO responseVo = new ResponseVO();
        responseVo.setInfo(msg);
        responseVo.setCode(code);
        return responseVo;
    }

    /**
     * 操作有误，使用默认400错误码
     *
     * @param msg 提示信息
     */
    public static ResponseVO error(String msg) {
        Integer code = ResponseCodeEnum.CODE_400.getCode();
        return error(code, msg);
    }

    /**
     * 操作有误，只返回默认错误状态码
     */
    public static ResponseVO error() {
        return error(null);
    }

}
