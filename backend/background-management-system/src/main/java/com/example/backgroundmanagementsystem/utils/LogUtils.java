package com.example.backgroundmanagementsystem.utils;

import com.example.backgroundmanagementsystem.enums.OperationTypeEnum;

/**
 * 日志工具类
 */
public class LogUtils {

    /**
     * 生成操作日志
     * @param operation
     * @param object
     * @return
     */
    public static String generateLogOperation(OperationTypeEnum operation,String object){
        if(operation==OperationTypeEnum.INSERT){
            return "新增了一个"+object;
        }else if(operation==OperationTypeEnum.UPDATE){
            return "修改了一个"+object;
        }else{
            return "删除了一个"+object;
        }
    }

}
