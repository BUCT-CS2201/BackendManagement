package com.example.backgroundmanagementsystem.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminLogVO {
    /**
     * 日志id
     */
    Long logId;

    /**
     * 管理员姓名
     */
    private String adminName;

    /**
     * 操作内容
     */
    private String operation;

    /**
     * 操作时间
     */
    private String operationTime;
}
