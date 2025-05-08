package com.example.backgroundmanagementsystem.pojo.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BaseEntity{
    /**
     * 公告id
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
    private LocalDateTime time;
    /**
     * 公告状态，0不公开，1公开
     */
    private Integer ispublic;
   
}
