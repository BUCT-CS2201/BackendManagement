package com.example.backgroundmanagementsystem.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 数据库备份与恢复
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataBaseBR extends BaseEntity{
    /**
     * id
     */
    private Integer id;
    /**
     * 用户名
     */
    private String adminName;
    /**
     * 备份注释
     */
    private String comment;
    /**
     * 备份路径
     */
    private String path;
}
