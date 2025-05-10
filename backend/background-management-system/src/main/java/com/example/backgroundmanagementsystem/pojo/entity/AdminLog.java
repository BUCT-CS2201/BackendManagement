package com.example.backgroundmanagementsystem.pojo.entity;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;

public class AdminLog {
    /** 
     * 日志ID
     */
    @NotEmpty
    private Long logId;
    /**
     * 管理员名称
     */
    @NotEmpty
    private String adminName;
    /**
     * 操作内容
     */
    @NotEmpty
    private String operation;
    /**
     * 操作时间
     */
    @NotEmpty
    private LocalDateTime operationTime;
}
