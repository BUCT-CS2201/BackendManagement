package com.example.backgroundmanagementsystem.pojo.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 响应消息体
 */
@Data
public class ResponseVO<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String info;
    /**
     * 响应数据
     */
    private T data;
}
