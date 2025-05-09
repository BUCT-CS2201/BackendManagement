package com.example.backgroundmanagementsystem.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {
    /**
     * 用户id
     */
    private Long notice_id;
    /**
     * 标题
     */

    private String title;
    /**
     * 发布人
     */
    private String name;
    /**
     * 发布内容
     */

    private String content;
    /**
     * 发布时间
     */
    private String time;
    /**
     * 账户状态，0禁用，1启用
     */
    private Integer ispublic;
   
}
