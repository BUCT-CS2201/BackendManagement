package com.example.backgroundmanagementsystem.exceptions;

import com.example.backgroundmanagementsystem.enums.ResponseCodeEnum;
import lombok.Data;

@Data
public class BaseException extends RuntimeException{
    private Integer code;
    private String msg;

    public BaseException(ResponseCodeEnum responseCodeEnum){
        this(responseCodeEnum.getCode(),responseCodeEnum.getMsg());
    }
    public BaseException(Integer code,String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
